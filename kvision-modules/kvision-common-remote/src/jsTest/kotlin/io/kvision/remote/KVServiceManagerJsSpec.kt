package io.kvision.remote

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlin.test.*

@Suppress("UNUSED_PARAMETER", "RedundantSuspendModifier")
class Dummy {
    suspend fun param0fun(): String = ""
    suspend fun param1fun(p1: Int): String = ""
    suspend fun param2fun(p1: Int, p2: Int): String = ""
    suspend fun param3fun(p1: Int, p2: Int, p3: Int): String = ""
    suspend fun param4fun(p1: Int, p2: Int, p3: Int, p4: Int): String = ""
    suspend fun param5fun(p1: Int, p2: Int, p3: Int, p4: Int, p5: Int): String = ""
    suspend fun param6fun(p1: Int, p2: Int, p3: Int, p4: Int, p5: Int, p6: Int): String = ""
    suspend fun websocketFun(r: ReceiveChannel<Int>, s: SendChannel<Int>) {}
    suspend fun tabulatorRemoteFun(p1: Int?, p2: Int?, p3: List<RemoteFilter>?, p4: List<RemoteSorter>?, p5: String?): RemoteData<Dummy> {
        throw UnsupportedOperationException()
    }
}

val ALL_NON_WS_BIND_CALLS: List<(serviceManager: KVServiceManager, method: HttpMethod, route: String?) -> Unit> = listOf(
    { serviceManager, method, route -> serviceManager.bind(Dummy::param0fun, method, route) },
    { serviceManager, method, route -> serviceManager.bind(Dummy::param1fun, method, route) },
    { serviceManager, method, route -> serviceManager.bind(Dummy::param2fun, method, route) },
    { serviceManager, method, route -> serviceManager.bind(Dummy::param3fun, method, route) },
    { serviceManager, method, route -> serviceManager.bind(Dummy::param4fun, method, route) },
    { serviceManager, method, route -> serviceManager.bind(Dummy::param5fun, method, route) },
    { serviceManager, method, route -> serviceManager.bind(Dummy::param6fun, method, route) },
    { serviceManager, method, route -> serviceManager.bindTabulatorRemote(Dummy::tabulatorRemoteFun, route) },
)

val ALL_NON_WS_CALL_NAMES = listOf(
    getCallName(Dummy::param0fun),
    getCallName(Dummy::param1fun),
    getCallName(Dummy::param2fun),
    getCallName(Dummy::param3fun),
    getCallName(Dummy::param4fun),
    getCallName(Dummy::param5fun),
    getCallName(Dummy::param6fun),
    getCallName(Dummy::tabulatorRemoteFun),
)

class KVServiceManager : KVServiceManagerJs<Dummy>()

class KVServiceManagerJsSpec {

    private lateinit var serviceManager: KVServiceManager

    @BeforeTest
    fun beforeMethod() {
        serviceManager = KVServiceManager()
    }

    @Test
    fun bind_addsFunctions() {
        // execution
        ALL_NON_WS_BIND_CALLS.forEach { it(serviceManager, HttpMethod.POST, null) }

        // evaluation
        val registry = serviceManager.calls.entries.sortedBy { it.value.first }

        assertEquals(ALL_NON_WS_BIND_CALLS.size, registry.size, "number of registered endpoints")
        for (i in ALL_NON_WS_BIND_CALLS.indices) {
            assertEndpointMatches(
                endpoint = registry[i],
                functionName = ALL_NON_WS_CALL_NAMES[i],
                httpMethod = HttpMethod.POST,
                expectedRoute = "/kv/routeKVServiceManager$i"
            )
        }
    }

    @Test
    fun bind_addsFunctions_withCustomRouteIfSupplied() {
        // execution
        ALL_NON_WS_BIND_CALLS.forEachIndexed { index, bind ->
            bind(serviceManager, HttpMethod.POST, "customRoute$index")
        }

        // evaluation
        val registry = serviceManager.calls.entries.sortedBy { it.value.first }
        assertEquals(ALL_NON_WS_BIND_CALLS.size, registry.size, "number of registered endpoints")
        for (i in ALL_NON_WS_BIND_CALLS.indices) {
            assertEndpointMatches(
                endpoint = registry[i],
                functionName = ALL_NON_WS_CALL_NAMES[i],
                httpMethod = HttpMethod.POST,
                expectedRoute = "/kv/customRoute$i"
            )
        }
    }

    @Test
    fun bind_failsIfMethodIsGetAndFunctionHasParameters() {
        // execution
        ALL_NON_WS_BIND_CALLS.subList(1, ALL_NON_WS_BIND_CALLS.size - 1).forEachIndexed { index, it ->
            assertFailsWith<UnsupportedOperationException>("Fun with params[$index]") {
                it(serviceManager, HttpMethod.GET, null)
            }
        }
    }

    @Test
    fun bind_addsWebsocketFunction() {
        // execution
        serviceManager.bind(Dummy::websocketFun, null)

        // evaluation
        assertEndpointMatches(
            endpoint = serviceManager.calls.entries.single(),
            functionName = getCallName(Dummy::websocketFun),
            httpMethod = HttpMethod.POST,
            expectedRoute = "/kvws/routeKVServiceManager0"
        )
    }

    @Test
    fun bind_addsWebsocketFunctionWithCustomRoute_ifSupplied() {
        // execution
        serviceManager.bind(Dummy::websocketFun, "customWsRoute")

        // evaluation
        assertEndpointMatches(
            endpoint = serviceManager.calls.entries.single(),
            functionName = getCallName(Dummy::websocketFun),
            httpMethod = HttpMethod.POST,
            expectedRoute = "/kvws/customWsRoute"
        )
    }

    @Test
    fun bind_addFunctionIfMethodIsGetAndFunctionHasNoParameters() {
        // execution
        serviceManager.bind(Dummy::param0fun, HttpMethod.GET, null)

        // evaluation
        assertEndpointMatches(
            endpoint = serviceManager.calls.entries.single(),
            functionName = getCallName(Dummy::param0fun),
            httpMethod = HttpMethod.GET,
            expectedRoute = "/kv/routeKVServiceManager0"
        )
    }



    private fun assertEndpointMatches(
        endpoint: Map.Entry<String, Pair<String, HttpMethod>>,
        functionName: String,
        httpMethod: HttpMethod,
        expectedRoute: String
    ) {
        val actualCallName = endpoint.key
        val (actualPath, actualHttpMethod) = endpoint.value

        assertEquals(functionName, actualCallName)
        assertEquals(httpMethod, actualHttpMethod, "http method")
        assertEquals(actualPath, expectedRoute)
    }
}
