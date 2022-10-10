/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.kvision.ksp

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSValueParameter
import com.google.devtools.ksp.validate
import io.kvision.annotations.KVBinding
import io.kvision.annotations.KVBindingMethod
import io.kvision.annotations.KVBindingRoute
import io.kvision.annotations.KVService
import io.kvision.annotations.KVServiceException
import java.io.File

data class NameDetails(
    val packageName: String,
    val className: String,
    val interfaceName: String,
    val managerName: String
)

data class ExceptionNameDetails(val packageName: String, val className: String)

@OptIn(KspExperimental::class)
class KVProcessor(
    private val codeGenerator: CodeGenerator,
) : SymbolProcessor {

    private var isInitialInvocation = true

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (!isInitialInvocation) {
            // A subsequent invocation is for processing generated files. We do not need to process these.
            return emptyList()
        }
        isInitialInvocation = false
        val services = mutableListOf<NameDetails>()
        val deps = resolver.getSymbolsWithAnnotation(KVService::class.qualifiedName.orEmpty())
            .filterIsInstance<KSClassDeclaration>().filter(KSNode::validate)
            .filter { it.classKind == ClassKind.INTERFACE }
            .mapNotNull { classDeclaration ->
                val interfaceName = classDeclaration.simpleName.asString()
                val isOldConvention = interfaceName.startsWith("I") && interfaceName.endsWith("Service")
                val packageName = classDeclaration.packageName.asString()
                val className = if (isOldConvention) interfaceName.drop(1) else "${interfaceName}Impl"
                val managerName = if (isOldConvention) "${className}Manager" else "${interfaceName}Manager"
                val dependencies = classDeclaration.containingFile?.let { Dependencies(true, it) } ?: Dependencies(true)
                codeGenerator.createNewFile(dependencies, packageName, className).writer().use {
                    when (codeGenerator.generatedFile.first().toString().sourceSetBelow("ksp")) {
                        "commonMain" -> {
                            it.write(
                                generateCommonCode(
                                    packageName,
                                    className,
                                    interfaceName,
                                    managerName,
                                    classDeclaration
                                )
                            )
                        }

                        "frontendMain" -> {
                            it.write(
                                generateFrontendCode(
                                    packageName,
                                    className,
                                    interfaceName,
                                    managerName,
                                    classDeclaration
                                )
                            )
                        }
                    }
                }
                services.add(NameDetails(packageName, className, interfaceName, managerName))
                classDeclaration.containingFile
            }.toList().toTypedArray()
        codeGenerator.createNewFile(Dependencies(true, *deps), "io.kvision.remote", "GeneratedKVServiceManager")
            .writer().use {
                when (codeGenerator.generatedFile.first().toString().sourceSetBelow("ksp")) {
                    "commonMain" -> {
                        it.write(generateCommonCodeFunctions(services))
                    }

                    "frontendMain" -> {
                        it.write(generateFrontendCodeFunctions(services))
                    }
                }
            }
        val exceptions = mutableListOf<ExceptionNameDetails>()
        val depsExceptions = resolver.getSymbolsWithAnnotation(KVServiceException::class.qualifiedName.orEmpty())
            .filterIsInstance<KSClassDeclaration>().filter(KSNode::validate)
            .filter { it.classKind == ClassKind.CLASS }
            .mapNotNull { classDeclaration ->
                val className = classDeclaration.simpleName.asString()
                val packageName = classDeclaration.packageName.asString()
                exceptions.add(ExceptionNameDetails(packageName, className))
                classDeclaration.containingFile
            }.toList().toTypedArray()
        codeGenerator.createNewFile(
            Dependencies(true, *depsExceptions),
            "io.kvision.remote",
            "GeneratedKVServiceExceptions"
        ).writer().use {
            when (codeGenerator.generatedFile.first().toString().sourceSetBelow("ksp")) {
                "commonMain" -> {
                    it.write(generateCommonCodeExceptions(exceptions))
                }
            }
        }
        return emptyList()
    }

    private fun String.sourceSetBelow(startDirectoryName: String): String =
        substringAfter("${File.separator}$startDirectoryName${File.separator}").substringBefore("${File.separator}kotlin${File.separator}")
            .substringAfterLast(File.separatorChar)

    private fun generateCommonCode(
        packageName: String,
        className: String,
        interfaceName: String,
        managerName: String,
        ksClassDeclaration: KSClassDeclaration
    ): String {
        return StringBuilder().apply {
            appendLine("//")
            appendLine("// GENERATED by KVision")
            appendLine("//")
            appendLine("package $packageName")
            appendLine()
            appendLine("import io.kvision.remote.HttpMethod")
            appendLine("import io.kvision.remote.KVServiceManager")
            appendLine("import io.kvision.remote.registerKVisionServiceExceptions")
            appendLine()
            appendLine("expect class $className : $interfaceName")
            appendLine()
            appendLine("object $managerName : KVServiceManager<$className>($className::class) {")
            appendLine("    init {")
            appendLine("        registerKVisionServiceExceptions()")
            val wsMethods = mutableListOf<String>()
            ksClassDeclaration.getDeclaredFunctions().forEach {
                val params = it.parameters
                val wsMethod =
                    if (params.size == 2)
                        params.first().type.toString().startsWith("ReceiveChannel")
                    else false
                val kvBinding = it.getAnnotationsByType(KVBinding::class).firstOrNull()
                val kvBindingMethod = it.getAnnotationsByType(KVBindingMethod::class).firstOrNull()
                val kvBindingRoute = it.getAnnotationsByType(KVBindingRoute::class).firstOrNull()
                val (method, route) = if (kvBinding != null) {
                    val method = kvBinding.method.name
                    val route = kvBinding.route
                    "HttpMethod.$method" to "\"$route\""
                } else if (kvBindingMethod != null) {
                    val method = kvBindingMethod.method.name
                    "HttpMethod.$method" to null
                } else if (kvBindingRoute != null) {
                    val route = kvBindingRoute.route
                    "HttpMethod.POST" to "\"$route\""
                } else {
                    "HttpMethod.POST" to null
                }
                when {
                    it.returnType.toString().startsWith("RemoteData") ->
                        appendLine("        bindTabulatorRemote($interfaceName::${it.simpleName.asString()}, $route)")

                    wsMethod -> if (route == null) {
                        appendLine("        bind($interfaceName::${it.simpleName.asString()}, null as String?)")
                    } else {
                        appendLine("        bind($interfaceName::${it.simpleName.asString()}, $route)")
                    }

                    else -> if (!wsMethods.contains(it.simpleName.asString()))
                        appendLine("        bind($interfaceName::${it.simpleName.asString()}, $method, $route)")
                }
                if (wsMethod) wsMethods.add(it.simpleName.asString())
            }
            appendLine("    }")
            appendLine("}")
        }.toString()
    }

    private fun generateFrontendCode(
        packageName: String,
        className: String,
        interfaceName: String,
        managerName: String,
        ksClassDeclaration: KSClassDeclaration
    ): String {
        return StringBuilder().apply {
            appendLine("//")
            appendLine("// GENERATED by KVision")
            appendLine("//")
            appendLine("package $packageName")
            appendLine()
            appendLine("import org.w3c.fetch.RequestInit")
            appendLine("import io.kvision.remote.KVRemoteAgent")
            appendLine("import kotlinx.serialization.modules.SerializersModule")
            getTypes(ksClassDeclaration.getDeclaredFunctions()).sorted().forEach {
                appendLine("import $it")
            }
            appendLine()
            appendLine("actual class $className(serializersModules: List<SerializersModule>? = null, requestFilter: (suspend RequestInit.() -> Unit)? = null) : $interfaceName, KVRemoteAgent<$className>($managerName, serializersModules, requestFilter) {")
            val methodsCounts = ksClassDeclaration.getDeclaredFunctions().map {
                it.simpleName.asString()
            }.groupBy { it }.map { it.key to it.value.size }.toMap()
            val wsMethods = mutableListOf<String>()
            ksClassDeclaration.getDeclaredFunctions().forEach {
                val name = it.simpleName.asString()
                val params = it.parameters
                val wsMethod =
                    if (params.size == 2)
                        params.first().type.toString().startsWith("ReceiveChannel")
                    else false
                if (!wsMethod) {
                    if (!wsMethods.contains(name)) {
                        if (params.isNotEmpty()) {
                            when {
                                it.returnType.toString().startsWith("RemoteData") -> appendLine(
                                    "    override suspend fun $name(${
                                        getParameterList(
                                            params
                                        )
                                    }) = ${it.returnType!!.resolve().let { getTypeString(it) }}()"
                                )

                                else -> appendLine(
                                    "    override suspend fun $name(${getParameterList(params)}) = call($interfaceName::$name, ${
                                        getParameterNames(
                                            params
                                        )
                                    })"
                                )
                            }
                        } else {
                            appendLine("    override suspend fun $name() = call($interfaceName::$name)")
                        }
                    }
                } else {
                    appendLine("    override suspend fun $name(${getParameterList(params)}) {}")
                    val type1 = getTypeString(params[0].type.resolve()).replace("ReceiveChannel", "SendChannel")
                    val type2 = getTypeString(params[1].type.resolve()).replace("SendChannel", "ReceiveChannel")
                    val override = if ((methodsCounts[name] ?: 0) > 1) "override " else ""
                    appendLine("    ${override}suspend fun $name(handler: suspend ($type1, $type2) -> Unit) = webSocket($interfaceName::$name, handler)")
                }
                if (wsMethod) wsMethods.add(name)
            }
            appendLine("}")
        }.toString()
    }

    private fun generateCommonCodeFunctions(services: List<NameDetails>): String {
        return StringBuilder().apply {
            appendLine("//")
            appendLine("// GENERATED by KVision")
            appendLine("//")
            if (services.isNotEmpty()) {
                appendLine("package io.kvision.remote")
                appendLine()
                appendLine("import kotlin.reflect.KClass")
                appendLine()
                appendLine("@Suppress(\"UNCHECKED_CAST\")")
                appendLine("inline fun <reified T : Any> getServiceManager(): KVServiceManager<T> = when (T::class) {")
                services.forEach {
                    appendLine("    ${it.packageName}.${it.interfaceName}::class -> ${it.packageName}.${it.managerName} as KVServiceManager<T>")
                }
                appendLine("    else -> throw IllegalArgumentException(\"Unknown service \${T::class}\")")
                appendLine("}")
                appendLine()
                appendLine("fun getAllServiceManagers(): List<KVServiceManager<*>> = listOf(")
                services.forEach {
                    appendLine("    ${it.packageName}.${it.managerName},")
                }
                appendLine(")")
                appendLine()
                appendLine("fun getServiceManagers(vararg kclass: KClass<*>): List<KVServiceManager<*>> {")
                appendLine("    return kclass.map {")
                appendLine("        when (it) {")
                services.forEach {
                    appendLine("            ${it.packageName}.${it.interfaceName}::class -> ${it.packageName}.${it.managerName}")
                }
                appendLine("            else -> throw IllegalArgumentException(\"Unknown service \${it.simpleName}\")")
                appendLine("        }")
                appendLine("    }")
                appendLine("}")
            }
            appendLine()
        }.toString()
    }

    private fun generateFrontendCodeFunctions(services: List<NameDetails>): String {
        return StringBuilder().apply {
            appendLine("//")
            appendLine("// GENERATED by KVision")
            appendLine("//")
            if (services.isNotEmpty()) {
                appendLine("package io.kvision.remote")
                appendLine()
                appendLine("import org.w3c.fetch.RequestInit")
                appendLine("import kotlinx.serialization.modules.SerializersModule")
                appendLine()
                appendLine("inline fun <reified T : Any> getService(serializersModules: List<SerializersModule>? = null, noinline requestFilter: (suspend RequestInit.() -> Unit)? = null): T = when (T::class) {")
                services.forEach {
                    appendLine("    ${it.packageName}.${it.interfaceName}::class -> ${it.packageName}.${it.className}(serializersModules, requestFilter) as T")
                }
                appendLine("    else -> throw IllegalArgumentException(\"Unknown service \${T::class}\")")
                appendLine("}")
            }
            appendLine()
        }.toString()
    }

    private fun generateCommonCodeExceptions(exceptions: List<ExceptionNameDetails>): String {
        return StringBuilder().apply {
            appendLine("//")
            appendLine("// GENERATED by KVision")
            appendLine("//")
            appendLine("package io.kvision.remote")
            appendLine()
            if (exceptions.isNotEmpty()) {
                appendLine("import kotlinx.serialization.json.Json")
                appendLine("import kotlinx.serialization.modules.SerializersModule")
                appendLine("import kotlinx.serialization.modules.polymorphic")
                appendLine("import kotlinx.serialization.modules.subclass")
                appendLine()
                appendLine("private var registered = false")
                appendLine()
                appendLine("fun registerKVisionServiceExceptions() {")
                appendLine("    if (!registered) {")
                appendLine("        RemoteSerialization.customConfiguration = Json {")
                appendLine("            serializersModule = SerializersModule {")
                appendLine("                polymorphic(AbstractServiceException::class) {")
                exceptions.forEach {
                    appendLine("                    subclass(${it.packageName}.${it.className}::class)")
                }
                appendLine("                }")
                appendLine("            }")
                appendLine("        }")
                appendLine("        registered = true")
                appendLine("    }")
                appendLine("}")
            } else {
                appendLine("fun registerKVisionServiceExceptions() {}")
            }
            appendLine()
        }.toString()
    }

    private fun getTypeString(type: KSType): String {
        val baseType = if (type.arguments.isEmpty()) {
            type.declaration.simpleName.asString()
        } else {
            type.declaration.simpleName.asString() + type.arguments.joinToString(",", "<", ">") {
                it.type?.let { getTypeString(it.resolve()) } ?: it.toString()
            }
        }
        return if (type.isMarkedNullable) "$baseType?" else baseType
    }

    private fun getParameterList(params: List<KSValueParameter>): String {
        return params.filter { it.name != null }.joinToString(", ") {
            "${it.name!!.asString()}: ${getTypeString(it.type.resolve())}"
        }
    }

    private fun getParameterNames(params: List<KSValueParameter>): String {
        return params.filter { it.name != null }.joinToString(", ") {
            it.name!!.asString()
        }
    }

    private fun getTypes(type: KSType): Set<String> {
        return if (type.arguments.isNotEmpty() && type.declaration.qualifiedName != null) {
            (type.arguments.flatMap {
                if (it.type != null) {
                    getTypes(it.type!!.resolve())
                } else {
                    emptySet()
                }
            } + type.declaration.qualifiedName!!.asString()).toSet()
        } else if (type.declaration.qualifiedName != null) {
            setOf(type.declaration.qualifiedName!!.asString())
        } else {
            emptySet()
        }
    }

    private fun getTypes(methods: Sequence<KSFunctionDeclaration>): Set<String> {
        return methods.flatMap { m ->
            m.parameters.flatMap { p ->
                getTypes(p.type.resolve())
            }.toSet() + (m.returnType?.let { getTypes(it.resolve()) } ?: setOf())
        }.filterNot {
            it.startsWith("kotlin.collections.") || it.startsWith("kotlin.")
        }.toSet()
    }
}
