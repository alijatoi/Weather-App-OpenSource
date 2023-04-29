//package com.example.weatherappusingopenmeteo.extras
//
//import android.Manifest
//import android.app.Activity
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.content.Context
//import android.content.pm.PackageManager
//import android.os.Build
//import androidx.core.app.ActivityCompat
//import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationManagerCompat
//import androidx.navigation.NavDeepLinkBuilder
//import androidx.work.Worker
//import androidx.work.WorkerParameters
//import com.example.weatherappusingopenmeteo.R
//
//class MyWorker(private val context: Context, private val params: WorkerParameters) : Worker(context,params) {
//
//    override fun doWork(): Result {
//        displayNotification()
//        return Result.Success()
//
//    }
//
//    fun displayNotification(){
//         val channelID = "channel_Id"
//         val requestCode = 1001
//        val notificationId = System.currentTimeMillis().toInt()
//        val pendingIntent = NavDeepLinkBuilder(context)
//            .setGraph(R.navigation.nav_graph) // Set the navigation graph that contains the fragment
//            .setDestination(R.id.aboutFragment) // Set the destination fragment to launch
//            .createPendingIntent()
//
//        val builder = NotificationCompat.Builder(context, channelID)
//            .setSmallIcon(R.drawable.ic_heavysnow)
//            .setContentTitle("Good Weather")
//            .setContentText("Weather is really good")
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setContentIntent(pendingIntent)
//            .setAutoCancel(true)
//            .addAction(R.drawable.ic_drop,"Action",pendingIntent)
//
//        val bigTextStyle = NotificationCompat.BigTextStyle()
//            .bigText("This is a long text which will be shown when the notification is expanded.")
//            .setBigContentTitle("Expanded Title")
//            .setSummaryText("Summary Text")
//        builder.setStyle(bigTextStyle)
//
//        //for showing progress bar
//        //builder.setProgress(100, 0, false)
//
//        // If Android Version is 8 or more. then create channel
//        //  Because Channel is required for Android 8 and newer versions
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//            val channel = NotificationChannel(
//                channelID,
//                "Weather Updates",
//                NotificationManager.IMPORTANCE_DEFAULT
//            ).apply {
//                description = "Weather Updates Frequently"
//            }
//
//            val notificationManager = NotificationManagerCompat.from(context)
//            notificationManager.createNotificationChannel(channel)
//
//        }
//        // Asking for run time permissions for Android 13 and newer Versions
//        with(NotificationManagerCompat.from(context)) {
//            if (ActivityCompat.checkSelfPermission( context, Manifest.permission.POST_NOTIFICATIONS)
//                != PackageManager.PERMISSION_GRANTED
//            ) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                    ActivityCompat.requestPermissions(context as Activity,
//                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
//                        requestCode )
//                }
//            }
//            else {
//                notify(notificationId, builder.build())
//            }
//        }
//    }
//}