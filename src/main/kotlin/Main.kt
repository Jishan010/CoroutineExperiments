import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

@OptIn(DelicateCoroutinesApi::class)
fun main() {
    runBlocking {

        //example for SupervisorJob
        val supervisorJob = SupervisorJob()

        val job1 = launch(supervisorJob) {
            delay(1000)
            // switches from current dispather and opens new corotuine scope
          /*  withContext(Dispatchers.IO){
                println("Job 1 completed.")
            }*/
            println("Job 1 completed.")
        }

        val job2 = launch(supervisorJob) {
            delay(2000)
            throw Exception("Job 2 failed!")
        }

        job1.join()
        job2.join()

        println("Jobs completed.")

        // example for supervisorScope
        /* supervisorScope {
             val job1 = launch {
                 delay(1000)
                 println("Job 1 completed.")
             }

             val job2 = launch {
                 delay(2000)
                 throw Exception("Job 2 failed!")
             }

             val job3 = launch {
                 delay(1000)
                 println("Job 1 completed.")
             }

             job1.join()
             job2.join()
             job3.join()
         }
         println("supervisorScope completed.")*/

        /* val job = CoroutineScope(Dispatchers.Default).launch {

             val timeTaken = measureTimeMillis {
                 val deferred1 = async { doSomethingAsync() }
                 val deferred2 = async { doAnotherThingAsync() }

                 // These operations are initiated concurrently

                 // Wait for both deferred values to be ready
                 val result1 = deferred1.await()
                 val result2 = deferred2.await()

                 println("Result 1: $result1")
                 println("Result 2: $result2")
             }

             println("Total time taken: ${timeTaken / 1000.0} seconds")
         }

         job.join()*/
    }
}

suspend fun doSomethingAsync(): String {
    // Simulate some asynchronous operation
    delay(5000) // Wait for 7 seconds
    return "Value from doSomethingAsync!"
}

suspend fun doAnotherThingAsync(): String {
    // Simulate another asynchronous operation
    delay(4000) // Wait for 12 seconds
    return "Value from doAnotherThingAsync!"
}
