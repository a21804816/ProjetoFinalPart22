package com.example.projetofinalpart1.data

import android.app.Application
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import okhttp3.OkHttpClient

class FilmeApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    FilmeRepository.init(
      local = FilmeRoom(MovieDatabase.getInstance(this).movieDao()),
      remote = Search("http://www.omdbapi.com/", "b0cf79db", OkHttpClient()),
      context = this
    )
    Log.i("APP", "Initialized repository")
  }

}