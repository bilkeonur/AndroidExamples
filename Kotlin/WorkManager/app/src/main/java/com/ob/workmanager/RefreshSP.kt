package com.ob.workmanager

import android.content.Context
import android.content.SharedPreferences
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters


class RefreshSP(val context:Context, workerParams:WorkerParameters): Worker(context,workerParams) {
    override fun doWork(): Result {
        val data: Data = inputData
        val incr: Int = data.getInt("incr", 0)
        refreshSP(incr)
        return Result.success()
        //return Result.failure()
        //return Result.retry()
    }

    private fun refreshSP(incr: Int) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("com.ob.workmanager", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        var number = sharedPreferences.getInt("number", 0)
        number = number + incr
        editor.putInt("number", number)
        editor.apply()
        println("Number :${number}")
    }
}