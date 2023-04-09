package com.example.weatherappusingopenmeteo.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavDeepLinkBuilder
import com.example.weatherappusingopenmeteo.R

class Notification (private  val context : Context) {
    //Channel ID to Differentiate between different channels category 
    val CHANNEL_ID = "channel_Id"
    val notificationId = System.currentTimeMillis().toInt()

    fun runNotification() {

        // NavDeepLinkBuilder use to show specfic fragment when the user touch/tap the notification
        // In this case, About Fragment will be shown
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_graph) // Set the navigation graph that contains the fragment
            .setDestination(R.id.aboutFragment) // Set the destination fragment to launch
            .createPendingIntent()


        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_heavysnow)
            .setContentTitle("Good Weather")
            .setContentText("Weather is really good")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                "Weather Updates",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Weather Updates Frequently"
            }
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.createNotificationChannel(channel)

        }
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }

            notify(notificationId, builder.build())
        }

    }
}
