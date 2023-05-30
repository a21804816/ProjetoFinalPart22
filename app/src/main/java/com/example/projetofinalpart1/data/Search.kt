package com.example.projetofinalpart1.data

import android.util.Log
import com.example.projetofinalpart1.model.Movie
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class Search(
    private val baseUrl: String,
    private val apiKey: String,
    private val client: OkHttpClient
) {

    fun checkIfFilmExist(query: String, callback: (Movie?, Throwable?) -> Unit) {
        val url = "$baseUrl/?apikey=$apiKey&s=$query"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i("APP", "null on failure")
                callback(null, e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    Log.i("APP", " null exception")
                    callback(null, IOException("Unexpected code ${response.code}"))

                } else {
                    val body = response.body?.string()
                    var movie=Movie("","","","")
                    if (body != null) {
                        val jsonObject = JSONObject(body)
                        val searchResult = jsonObject.optJSONArray("Search")
                        searchResult?.let { jsonArray ->
                            for (i in 0 until jsonArray.length()) {
                                val movieObject = jsonArray.getJSONObject(i)
                                val title = movieObject.optString("Title")
                                val year = movieObject.optString("Year")
                                val imdbId = movieObject.optString("imdbID")
                                val posterUrl = movieObject.optString("Poster")
                                if (title.equals(query, ignoreCase = true)) {
                                    movie = Movie(title, year, imdbId, posterUrl)
                                    Log.i("APP", " $movie")
                                    callback(movie, null)
                                    break
                                }
                            }
                        }
                    } else {
                        Log.i("APP", " empty")
                        callback(null, IOException("Empty response body"))
                    }
                }
            }
        })
    }
}