import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = launch {
        try {
            repeat(1000) { index ->
                ensureActive() // Checks the cancellation status
                println("Coroutine is still active: $index")
                delay(100)
            }
        } catch (ex: Exception) {
            if (ex is CancellationException) {
                println("Yoyo : ${ex.message}")
            }
        }
    }

    delay(2000)
    job.cancel() // Cancels the coroutine after 2000ms
    job.join()

    println("Coroutine completed or canceled.")
}
