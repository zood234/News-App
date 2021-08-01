package com.example.newsapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*

class Receiver() : BroadcastReceiver(){
    val CHANNEL_ID = "chanel_id_example_01"
    val notificationId = 101
    override fun onReceive(context: Context, intent: Intent?) {

        var mcontext = context
        Log.d("MainActivity", "Receiver : "+ Date().toString())


        val builder = NotificationCompat.Builder(mcontext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("There are new articles for your search query")
            .setContentText("Example Text for content")
            .setPriority((NotificationCompat.PRIORITY_DEFAULT))

        with(NotificationManagerCompat.from(mcontext)){
            notify(notificationId, builder.build())

        }
    }}