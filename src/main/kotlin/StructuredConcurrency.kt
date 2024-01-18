import kotlinx.coroutines.*

// coroutine exception handling cheet sheet https://www.lukaslechner.com/coroutines-exception-handling-cheat-sheet/
fun main() {
    runBlocking {

        /* // Example 1: Structured Concurrency with launch
         val job1 = launch {
             println("Task 1 started")
             delay(1000)
             println("Task 1 completed")
         }

         val job2 = launch {
             println("Task 2 started")
             delay(500)
             println("Task 2 completed")
         }

         job1.join()
         job2.join()
 */
        // Example 2: Structured Concurrency with coroutineScope
        try {
            supervisorScope {
                launch {
                    println("Child Task 1 started")
                    delay(1500)
                    println("Child Task 1 completed")
                }


                val defferedJob1 = async {
                    getResult()
                }
                val defferedJob2 = async {
                    getResult()
                }

                println("results ${awaitAll(defferedJob1, defferedJob2)}")



                launch {
                    println("Child Task 2 started")
                    delay(1000)
                    // Uncomment to simulate an exception in the child scope
                    //throw RuntimeException("Something went wrong in child scope")
                    println("Child Task 2 completed")
                }

                // Simulate some work in the parent scope
                delay(500)

                // Uncomment to simulate an exception in the parent scope
                throw RuntimeException("Something went wrong in parent scope")
            }
        } catch (e: Exception) {
            println("Exception in parent scope: $e")
        }

    }
}

suspend fun getResult(): String {
    delay(2000)
    return "This is your test results"
}
