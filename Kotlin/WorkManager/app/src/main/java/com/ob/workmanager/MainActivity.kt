package com.ob.workmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data: Data = Data.Builder().putInt("incr", 1).build()
        val constraints: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .build()

        //Single
        //Single
        val workRequestSingle: WorkRequest = OneTimeWorkRequest.Builder(RefreshSP::class.java)
            .setConstraints(constraints)
            .setInputData(data)
            .setInitialDelay(5, TimeUnit.SECONDS)
            .addTag("MyTag")
            .build()

        WorkManager.getInstance(this).enqueue(workRequestSingle)

        //Periodic Min 15 Minute
        //Periodic Min 15 Minute
        val workRequestPeriodic: WorkRequest = PeriodicWorkRequest.Builder(RefreshSP::class.java, 15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        WorkManager.getInstance(this).enqueue(workRequestPeriodic)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequestSingle.id).observe(this, Observer{
            if (it.state == WorkInfo.State.RUNNING) {
                println("Running")
            } else if (it.state == WorkInfo.State.SUCCEEDED) {
                println("succeeded")
            } else if (it.state == WorkInfo.State.FAILED) {
                println("Failed")
            }
        })

        //Cancel
        //WorkManager.getInstance(this).cancelAllWork();
        //WorkManager.getInstance(this).cancelWorkById(workRequestSingle.id);

        //Chaining (Sadece One Time Work Requestte Çalışır İlk İş Tamamlanınca İkinci İşi Başlat Şeklinde)
        val oneTimeWorkRequest: OneTimeWorkRequest = OneTimeWorkRequest.Builder(RefreshSP::class.java)
            .setInputData(data)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).beginWith(oneTimeWorkRequest)
            .then(oneTimeWorkRequest)
            .then(oneTimeWorkRequest)
            .enqueue()
    }
}