package com.example.test

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.*
import java.util.*
import java.util.concurrent.TimeUnit

class NotificationWork(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        //通知↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        val ic = R.drawable.ic_android_black_24dp
        val title = "通知"
        val main = "内容"
        val builder = NotificationCompat.Builder(applicationContext,"1")
            .setSmallIcon(ic)
            .setContentTitle(title)
            .setContentText(main)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val name = "タイトル"
        val descriptionText = "内容"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("1", name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(1,builder.build())
        //↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        //次回Workセット↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        val workManager = WorkManager.getInstance(applicationContext)
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

        //次回起動時刻の表示
        val text = Date(nowTime.timeInMillis + delay).toString()
        Log.i("次回Workセット",text)

        return Result.success()
        //↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
    }
}