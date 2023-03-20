package com.ob.delegation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

//2 Sınıftan Birden Kalıtım Alınamaz. Delegasyon -> O sebeple Arayüzü Implemente Edip Ordan Sınıfa Delege Ediyoruz (Bu Arayüzü Uygulayan Bu Sınıf)
class MainActivity : AppCompatActivity(), ILifecycleLogger by LifecycleLogger() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerLifecycleOwner(this)

        //Aktivite Yaşam Döngüsü
        //Uygulama Başladı
        //ON CREATE -> ON START -> ON RESUME
        //Arkaplana Gitti
        //ON PAUSE -> ON STOP
        //Tekrar Ön Plana Geldi
        //ON START -> ON RESUME
        //Komple Kapatıldı
        //ON PAUSE -> ON STOP -> ON DESTROY
        //Ekran Yan Çevrildi
        //ON DESTROY -> ON STOP -> ON DESTROY -> ON CREATE-> ON START -> ON RESUME
    }
}

interface ILifecycleLogger {
    fun registerLifecycleOwner(owner:LifecycleOwner)
}

class LifecycleLogger: ILifecycleLogger, LifecycleEventObserver {
    override fun registerLifecycleOwner(owner: LifecycleOwner) {
        owner.lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when(event) {
            Lifecycle.Event.ON_CREATE -> println("ON CREATE")
            Lifecycle.Event.ON_START -> println("ON START")
            Lifecycle.Event.ON_RESUME -> println("ON RESUME")
            Lifecycle.Event.ON_PAUSE -> println("ON PAUSE")
            Lifecycle.Event.ON_STOP -> println("ON STOP")
            Lifecycle.Event.ON_DESTROY -> println("ON DESTROY")
            Lifecycle.Event.ON_ANY -> println("ON ANY")
            else -> Unit
        }
    }
}