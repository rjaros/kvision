package io.kvision.gradle

import java.io.ByteArrayOutputStream
import org.gradle.process.ExecOperations
import org.gradle.process.ExecResult
import org.gradle.process.ExecSpec

/**
 * Run an executable, and capture the output.
 *
 * Note that [ExecSpec.isIgnoreExitValue] is enabled by default so the output can be logged before
 * re-throwing the exception ([ExecResult.rethrowFailure]). Alternatively, re-enable
 * [ExecSpec.isIgnoreExitValue] in [configure].
 */
internal fun ExecOperations.execCapture(
    configure: ExecSpec.() -> Unit,
): ExecCaptureResult {
    val (execResult, output) = ByteArrayOutputStream().use { output ->
        exec {
            isIgnoreExitValue = true

            standardOutput = output
            errorOutput = output

            configure()
        } to output.toString()
    }

    return ExecCaptureResult(execResult, output)
}

internal data class ExecCaptureResult(
    private val result: ExecResult,
    val output: String,
) : ExecResult by result {
    val isSuccess = result.exitValue == 0
}
