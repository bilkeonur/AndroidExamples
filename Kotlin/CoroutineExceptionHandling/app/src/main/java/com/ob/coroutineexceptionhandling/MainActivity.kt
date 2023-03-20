package com.ob.coroutineexceptionhandling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //1
        /*
        //Created Resumed veya Started Yapılabilir
        lifecycleScope.launch {
            throw Exception("Error")
            //Uygulama Direk Çöker Bunu try catch İçinde Yazarsak Uygulama Çökmez Fakat Her yerde try catch yazamayız
        }
        */

        //2 try catch içinde bile olsa uygulama çöker
        /*
        lifecycleScope.launch {
            try {
                launch {
                    throw Exception("Error")
                }
            }
            catch (e:java.lang.Exception) {
                e.printStackTrace()
            }
        }
        */

        //3 Uygulama Çökmeyecek
        /*
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("Exception : ${throwable.localizedMessage}");
        }

        lifecycleScope.launch(handler) {
            throw Exception("Error1")
        }

        lifecycleScope.launch(handler) {
            throw Exception("Error2")
        }
        */

        /*
        //4 Uygulama Çökmez fakat tek bir scobun içinde nekadar launch olursa olsun biti hata verdiğinde coroutine tüm skobun çalışmasını durdurur.
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("Exception : ${throwable.localizedMessage}");
        }

        lifecycleScope.launch(handler) {
            launch {
                throw Exception("Error3")
            }

            launch {
                delay(500L)
                println("This is Executed") //Çalışma Çünkü Yukarı Hata Verdi
            }
        }
        */

        //5

        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("Exception : ${throwable.localizedMessage}");
        }

        lifecycleScope.launch(handler) {
            //Gözlemci Scope
            supervisorScope {
                launch {
                    throw Exception("Error3")
                }

                launch {
                    delay(500L)
                    println("This is Executed") //Superviser Scope İçerisinde Olduğumuzdan Yukarıdaki Hata Bile Verse Bu kod çalışacak
                }
            }
        }

        //Bu şekildede Kullanılabilir
        CoroutineScope(Dispatchers.Main + handler).launch {

        }
    }
}