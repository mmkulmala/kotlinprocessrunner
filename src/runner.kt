import java.io.File
import java.lang.ProcessBuilder.Redirect
import java.util.concurrent.TimeUnit



fun main(args : Array<String>) {
    println("Run with String.runCommand()")
    "ls -la".runCommand()
    println("Run via bash script")
    "./run.sh 9".runCommand()
}

fun String.runCommand(workingDir: File? = null) {
    val process = ProcessBuilder(*split(" ").toTypedArray())
            .directory(workingDir)
            .redirectOutput(Redirect.INHERIT)
            .redirectError(Redirect.INHERIT)
            .start()
    if (!process.waitFor(10, TimeUnit.SECONDS)) {
        process.destroy()
        throw RuntimeException("execution timed out: $this")
    }
    if (process.exitValue() != 0) {
        throw RuntimeException("execution failed with code ${process.exitValue()}: $this")
    }
}
