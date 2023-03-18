package com.ob.workmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Data data = new Data.Builder().putInt("incr",1).build();
        Constraints constraints = new Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .build();

        //Single
        WorkRequest workRequestSingle = new OneTimeWorkRequest.Builder(RefreshSP.class)
            .setConstraints(constraints)
            .setInputData(data)
            .setInitialDelay(5, TimeUnit.SECONDS)
            .addTag("MyTag")
            .build();

        WorkManager.getInstance(this).enqueue(workRequestSingle);

        //Periodic Min 15 Minute
        WorkRequest workRequestPeriodic = new PeriodicWorkRequest.Builder(RefreshSP.class,15,TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInputData(data)
            .build();

        WorkManager.getInstance(this).enqueue(workRequestPeriodic);

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequestSingle.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo)
            {
                if(workInfo.getState() == WorkInfo.State.RUNNING)
                {
                    System.out.println("Running");
                }
                else if(workInfo.getState() == WorkInfo.State.SUCCEEDED)
                {
                    System.out.println("succeeded");
                }
                else if(workInfo.getState() == WorkInfo.State.FAILED)
                {
                    System.out.println("Failed");
                }
            }
        });

        //Cancel
        //WorkManager.getInstance(this).cancelAllWork();
        //WorkManager.getInstance(this).cancelWorkById(workRequestSingle.getId());

        //Chaining (Sadece One Time Work Requestte Çalışır İlk İş Tamamlanınca İkinci İşi Başlat Şeklinde)
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(RefreshSP.class)
            .setInputData(data)
            .setConstraints(constraints)
            .build();

        WorkManager.getInstance(this).beginWith(oneTimeWorkRequest)
            .then(oneTimeWorkRequest)
            .then(oneTimeWorkRequest)
            .enqueue();
    }
}