package com.ob.hilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity()
{
    @Inject //Field Injection
    lateinit var lars: Musician

    @Inject
    lateinit var myClass: ClassExample

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Injections
        //1-Field Injection
        //2-Contructor Injection

        //Scope
        //Android Class             Generated Component             Scope
        //Application               ApplicationComponent            @Singleton
        //View Model                ActivityRetainedComponent       @ActivityRetainedScope
        //Activity                  ActivityComponent               @ActivityScoped
        //Fragment                  FragmentComponent               @FragmentScoped
        //View                      ViewComponent                   @ViewScoped
        //View annotated with       ViewWithFragmmentComponent      @ViewScoped
        //@WithFragmentBindings
        //Service                   ServiceComponent                @ServiceScoped

        val instrument = Instrument()
        val band = Band()
        val james = Musician(instrument,band)
        james.sing("james")

        lars.sing("lars")

        println(myClass.myFunction())
        println(myClass.secondFunction())
        //Bunun Çalışabilmesi İçin Interface İçinde 1. yöntem olarak bindingFunction metodunu yazdık. 2. yöntem olarak providerFunction yazdık.
    }
}