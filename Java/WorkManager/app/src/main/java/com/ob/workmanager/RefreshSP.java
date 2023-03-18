package com.ob.workmanager;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import  androidx.work.Worker;
import androidx.work.WorkerParameters;

public class RefreshSP extends Worker
{
    Context context;

    public RefreshSP(@NonNull Context context, @NonNull WorkerParameters workerParams)
    {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork()
    {
        Data data = getInputData();
        int incr = data.getInt("incr",0);
        refreshSP(incr);
        return Result.success();
        //return Result.failure();
        //return Result.retry();
    }

    private void refreshSP(int incr)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.ob.workmanager",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int number = sharedPreferences.getInt("number",0);
        number = number + incr;
        editor.putInt("number",number);
        editor.commit();
        System.out.println("Number :" + number);
    }
}
