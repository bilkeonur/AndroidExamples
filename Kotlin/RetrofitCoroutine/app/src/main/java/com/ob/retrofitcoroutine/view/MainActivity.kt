package com.ob.retrofitcoroutine.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ob.retrofitcoroutine.R
import com.ob.retrofitcoroutine.model.CityModel
import com.ob.retrofitcoroutine.service.CityAPI
import kotlinx.coroutines.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val baseURL = "https://raw.githubusercontent.com/bilkeonur/"
    private var job: Job? = null

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error : ${throwable.localizedMessage}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //getDataWithoutCoroutine()
        getDataWithCoroutine()
    }

    private fun getDataWithCoroutine() {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CityAPI::class.java)

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.getData()

            //Main Threada Geç
            withContext(Dispatchers.Main) {
                if(response.isSuccessful) {
                    response.body()?.let { cities ->
                        for(city in cities) {
                            println("**************************")
                            println("Id : ${city.id}")
                            println("Name : ${city.name}")
                            println("Lat : ${city.lat}")
                            println("Lon : ${city.lon}")
                            println("**************************")
                        }
                    }
                }
            }
        }
    }

    private fun getDataWithoutCoroutine() {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CityAPI::class.java)
        val call = service.getCities()

        call.enqueue(object: Callback<List<CityModel>>{
            override fun onResponse(call: Call<List<CityModel>>, response: Response<List<CityModel>>) {
                if(response.isSuccessful) {
                    response.body()?.let { cities ->
                        for(city in cities) {
                            println("**************************")
                            println("Id : ${city.id}")
                            println("Name : ${city.name}")
                            println("Lat : ${city.lat}")
                            println("Lon : ${city.lon}")
                            println("**************************")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<CityModel>>, t: Throwable) {
                println("Error : ${t.message}")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}