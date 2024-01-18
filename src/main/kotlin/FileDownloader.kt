@file:Suppress("DEPRECATION")

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileOutputStream
import java.net.URL
import kotlin.system.measureTimeMillis

val scope =
    CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error message : ${throwable.message}")
    })

fun main() = runBlocking {
    while (true) {
        println("Please Input url of your choice")
        val url = readln()
        println("please provide save destination")
        val destination = readlnOrNull()
        println("url is $url and destination is  $destination")

        downloadFile(destination, url)

        println("do you want to exit ? Y and N")
        if (readlnOrNull() == "Y" || readlnOrNull() == null) {
            break
        }
    }
}

private suspend fun downloadFile(destination: String?, url: String) {
    val timeTaken = measureTimeMillis {
        destination?.let { destination ->
            println("Downloading file...")
            val job = scope.launch {
                download(URL(url), destination)
            }
            job.join()
        }
    }
    println("Time taken: $timeTaken milliseconds")
}

suspend fun download(
    url: URL = URL("https://www.apache.org/licenses/LICENSE-2.0.txt"),
    downloadPath: String = "${System.getProperty("user.home")}/Downloads/apache_license.txt" // default path is /Users/jmohiuddinansari/Downloads/apache_license.txt
) {
    withContext(Dispatchers.IO) {
        val connection = url.openConnection()
        val inputStream = connection.getInputStream()

        // Use the user's home directory for downloads on macOS
        val outputStream = FileOutputStream(downloadPath)
        val buffer = ByteArray(1024)
        var bytesRead = inputStream.read(buffer)
        while (bytesRead != -1) {
            outputStream.write(buffer, 0, bytesRead)
            bytesRead = inputStream.read(buffer)
        }
        outputStream.close()
        inputStream.close()
        println("File downloaded successfully to : $downloadPath")
    }
}