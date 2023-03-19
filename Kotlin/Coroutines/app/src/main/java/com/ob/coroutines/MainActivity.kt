package com.ob.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Global Scope -> Tüm Uygulama İçerisini Kapsayan Bir Scop
        //Run Blocking -> Kendisinden Sonraki Kodlar Bu Blok Çalışana Kadar Bloklanır.
        //Coroutine Scop -> Gerçek Hayatta Bunu Kullanırız

        //Dispatchers
        // Coroutine’in çalışacağı thread’ e karar veren yapıdır.
        //Dispatchers.Default -> CPU Intensive (Yoğun) işlerde Kullanılır. Örneğin Resim İşleme
        //Dispatchers.IO -> Networking İşlemleri
        //Dispatchers.Main -> UI İşlemleri İçin Kullanılır. UI Güncelleme
        //Dispatchers.Unconfined (Serbest,Sınırsız) -> Inherited Dispatcer İçerisinde Çalıştırılan Yere Göre Değişir.

        //Suspend Fonksiyon
        // İçerisinde Coroutine Çalıştıran fonksiyonlardır. İstenildiği zaman durdurulup devam ettirilebilir. Android ayarlamasını yapar
        //runExample7() -> Coroutine yada diğer suspend fonksiyon içerisinden çağırılabilir. Aşağıdaki Gibi

        //*********************************************
        runBlocking {
            delay(5000)
            println("Run Blocking")
            runExample7()
        }
        //*********************************************

        //*********************************************
        var userName = ""
        var userAge = 0

        runBlocking {
            val downloadedName = async {
                downloadName()
            }

            val downloadedAge = async {
                downloadAge()
            }

            userName = downloadedName.await()
            userAge = downloadedAge.await()

            println("${userName} ${userAge}")
        }
        //*********************************************

        //*********************************************

        //Job
        runBlocking {
            val myJob = launch {
                delay(2000)
            }

            myJob.invokeOnCompletion {
                println("My Job Completed")
            }

            myJob.cancel() //Yukarıdakilerin sonucu dönmeden iptal edildi
        }

        //*********************************************

        //withContext
        //Aynı Scop İçerisinde Farklı Threadlarla İşlemler Yapmamıza Olanak Sağlar.

        runBlocking {
            launch(Dispatchers.Default) {
                println("Context : ${coroutineContext}")
                withContext(Dispatchers.IO) {
                    println("Context : ${coroutineContext}")
                }
            }
        }
    }

    fun runExample1() {
        GlobalScope.launch {
            repeat(100000) {
                launch {
                    println("Onur Bilke")
                }
            }
        }
    }

    fun runExample2() {
        println("Run Blocking Start")

        runBlocking {
            launch {
                delay(5000)
                println("Run Blocking")
            }
        }

        //Run Blocking Bittiği Zaman Çalıştırıldı
        println("Run Blocking End")
    }

    fun runExample3() {
        println("Global Scope Start")

        GlobalScope.launch {
            launch {
                delay(5000)
                println("Global Scope")
            }
        }

        //Run Blocking Gibi Bloklama Olmaz Hemen Çalışır
        println("Global Scope End")
    }

    fun runExample4() {
        println("Coroutine Scope Start")

        CoroutineScope(Dispatchers.Default).launch {
            delay(5000)
            println("Coroutine Scope")
        }

        //Run Blocking Gibi Bloklama Olmaz Hemen Çalışır
        println("Coroutine Scope End")
    }

    fun runExample5() {
        //İç İçe
        runBlocking {
            launch {
                delay(5000)
                println("Run Blocking")
            }

            //runBlocking içerisinde olduğumuz için bu şekilde kullanabildik.
            //Suspend Function içerisindede bu şekilde kullanılabilir.
            coroutineScope {
                launch {
                    delay(3000)
                    println("Coroutine Scope")
                }
            }
        }
    }

    fun runExample6() {
        runBlocking {
            launch(Dispatchers.Main) {
                println("Main Thread : ${Thread.currentThread().name}")
            }

            launch(Dispatchers.IO) {
                println("IO Thread : ${Thread.currentThread().name}")
            }

            launch(Dispatchers.Default) {
                println("Default Thread : ${Thread.currentThread().name}")
            }

            launch(Dispatchers.Unconfined) {
                println("Unconfined Thread : ${Thread.currentThread().name}")
            }
        }
    }

    suspend fun runExample7() {
        coroutineScope {
            delay(3000)
            println("Suspend Function")
        }
    }

    suspend fun downloadName(): String {
        delay(5000)
        return "Onur Bilke"
    }

    suspend fun downloadAge(): Int {
        delay(7000)
        return 37
    }
}