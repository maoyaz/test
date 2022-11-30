package com.example.test

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val workManager = WorkManager.getInstance(this)

        //Workの消去
        workManager.cancelAllWorkByTag("weather-work")

        //現在時刻
        val nowTime = Calendar.getInstance()
        //次の起動時間
        val setTime = Calendar.getInstance()

        //次の日の午前00:00をセット
        setTime.set(Calendar.HOUR_OF_DAY,0)
        setTime.set(Calendar.MINUTE,0)
        setTime.set(Calendar.SECOND,0)
        setTime.set(Calendar.MILLISECOND,0)
        setTime.add(Calendar.HOUR_OF_DAY,24)

        //二つの時差をミリ秒で計算
        val delay = setTime.timeInMillis - nowTime.timeInMillis


        //毎日午前00:00に一回通知を送るWork
        val request = OneTimeWorkRequestBuilder<NotificationWork>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .addTag("weather-work")
            .build()
        workManager.enqueue(request)

        //次回実行時間をログで表示
        val text = Date(nowTime.timeInMillis + delay).toString()
        Log.i("次回Workセット",text)
    }
}