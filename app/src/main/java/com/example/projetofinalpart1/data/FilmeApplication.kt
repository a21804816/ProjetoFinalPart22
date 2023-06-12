package com.example.projetofinalpart1.data

import android.app.Application
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class FilmeApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    ConnectivityUtil.isOnline(this)
    FilmeRepository.init(
      local = FilmeRoom(FilmsDatabase.getInstance(this).filmDao()),
      remote = FilmeOkHttp("http://www.omdbapi.com/", "b0cf79db", OkHttpClient()),
      context = this
    )
    Log.i("APP", "Initialized repository")
    getFilmById("tt0068646")
    getFilmById("tt0468569")
    getFilmById("tt0071562")
    getFilmById("tt0050083")
    getFilmById("tt0108052")
    getFilmById("tt0167260")
    getFilmById("tt0110912")
    getFilmById("tt0120737")
    getFilmById("tt0060196")
    getFilmById("tt9362722")
    getFilmById("tt0109830")
    getFilmById("tt0137523")

  }

  private fun getFilmById(imdbId:String){
    val repository = FilmeRepository.getInstance()
    CoroutineScope(Dispatchers.IO).launch {
      repository.checkIfFilmExistDasboard(imdbId){
          i,a->println(i)
      }
    }
  }

}