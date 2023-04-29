//package com.example.weatherappusingopenmeteo.extras
//
//import android.app.AlarmManager
//import android.app.PendingIntent
//import android.content.Context
//import android.content.Intent
//import android.os.Build
//import android.os.SystemClock
//import androidx.core.content.ContentProviderCompat.requireContext
//import Notification
//
//val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
//val intent = Intent(requireContext(), Notification::class.java)
//val pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent,
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE else 0
//)
//val triggerTime = SystemClock.elapsedRealtime() + 60 * 10
//alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, triggerTime+5000, pendingIntent)
