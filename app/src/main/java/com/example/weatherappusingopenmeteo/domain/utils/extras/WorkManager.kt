//package com.example.weatherappusingopenmeteo.extras
//
//import android.util.Log
//import androidx.work.Constraints
//import androidx.work.OneTimeWorkRequestBuilder
//import androidx.work.WorkRequest
//
//val constraints = Constraints.Builder()
//    .setRequiresCharging(true)
//    .build()
//
//val request: WorkRequest =
//    OneTimeWorkRequestBuilder<MyWorker>()
//        .build()
//
//WorkManager.getInstance(requireContext()).enqueue(request)
//WorkManager.getInstance(requireContext()).getWorkInfoByIdLiveData(request.id).observe(requireActivity(), Observer {
//    Log.d("Valuee",it.toString())
//})