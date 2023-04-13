package com.example.weatherappusingopenmeteo.utils

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.weatherappusingopenmeteo.R

class Notification () : BroadcastReceiver() {
    private val channelID = "channel_Id"
    private val notificationId = System.currentTimeMillis().toInt() //this will create new notification ID based on current Time.
    override fun onReceive(context: Context, intent: Intent) {
        val requestCode = 1001
        Log.d("Check","Checkk")

        // NavDeepLinkBuilder use to show specific fragment when the user touch/tap the notification
        // In this case, About Fragment will be shown
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_graph) // Set the navigation graph that contains the fragment
            .setDestination(R.id.aboutFragment) // Set the destination fragment to launch
            .createPendingIntent()

        val builder = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_heavysnow)
            .setContentTitle("Good Weather")
            .setContentText("Weather is really good")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .addAction(R.drawable.ic_drop,"Action",pendingIntent)
            .setTimeoutAfter(15000)

        val bigTextStyle = NotificationCompat.BigTextStyle()
            .bigText("This is a long text which will be shown when the notification is expanded.")
            .setBigContentTitle("Expanded Title")
            .setSummaryText("Summary Text")
        builder.setStyle(bigTextStyle)

        //for showing progress bar
        //builder.setProgress(100, 0, false)

        // If Android Version is 8 or more. then create channel
        //  Because Channel is required for Android 8 and newer versions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                channelID,
                "Weather Updates",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Weather Updates Frequently"
            }

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.createNotificationChannel(channel)

        }
        // Asking for run time permissions for Android 13 and newer Versions
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission( context, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(context as Activity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    requestCode )
            }
            else {
                notify(notificationId, builder.build())
            }
        }
    }

    }


