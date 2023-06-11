package com.example.projetofinalpart1.data

import android.util.Log
import com.example.projetofinalpart1.model.FilmeApi
import com.example.projetofinalpart1.model.FilmeDashboard
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class FilmeOkHttp(
    private val baseUrl: String = "https://www.omdbapi.com",
    private val apiKey: String,
    private val client: OkHttpClient
) {

    fun checkIfFilmExistDashboard(imdbId: String, callback: (FilmeDashboard?, Throwable?) -> Unit) {
        val url = "$baseUrl/?apikey=$apiKey&i=$imdbId"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null, e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    callback(null, IOException("Unexpected code ${response.code}"))
                } else {
                    val body = response.body?.string()
                    if (body != null) {
                        val jsonObject = JSONObject(body)
                        val title = jsonObject.optString("Title")
                        if (title.isNotEmpty()) {
                            val released = jsonObject.optString("Released")
                            val runtime = jsonObject.optString("Runtime")
                            val genre = jsonObject.optString("Genre")
                            val actors = jsonObject.optString("Actors")
                            val plot = jsonObject.optString("Plot")
                            val poster = jsonObject.optString("Poster")
                            val imdbRating = jsonObject.optString("imdbRating")
                            val imdbVotes = jsonObject.optString("imdbVotes")
                            val type = jsonObject.optString("Type")

                            val filmeDashboard = FilmeDashboard(
                                title,
                                released,
                                runtime,
                                genre,
                                actors,
                                plot,
                                poster,
                                imdbRating,
                                imdbVotes,
                                imdbId,
                                type
                            )
                            callback(filmeDashboard, null)
                        } else {
                            callback(null, IOException("Film not found"))
                        }
                    } else {
                        callback(null, IOException("Empty response body"))
                    }
                }
            }
        })
    }

    fun checkIfFilmExist(query: String, callback: (FilmeApi?, Throwable?) -> Unit) {
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
                    if (body != null) {
                        val jsonObject = JSONObject(body)
                        val searchResult = jsonObject.optJSONArray("Search")
                        if (searchResult != null && searchResult.length() > 0) {
                            for (i in 0 until searchResult.length()) {
                                val movieObject = searchResult.getJSONObject(i)
                                val title = movieObject.optString("Title")
                                if (title.equals(query, ignoreCase = true)) {
                                    val imdbID = movieObject.optString("imdbID")

                                    val movieRequestUrl = "$baseUrl/?apikey=$apiKey&i=$imdbID"
                                    val movieRequest = Request.Builder()
                                        .url(movieRequestUrl)
                                        .build()

                                    client.newCall(movieRequest).enqueue(object : Callback {
                                        override fun onFailure(call: Call, e: IOException) {
                                            callback(null, e)
                                        }

                                        override fun onResponse(call: Call, response: Response) {
                                            if (!response.isSuccessful) {
                                                callback(null, IOException("Unexpected code ${response.code}"))
                                            } else {
                                                val movieBody = response.body?.string()
                                                if (movieBody != null) {
                                                    val movieJsonObject = JSONObject(movieBody)
                                                    val released = movieJsonObject.optString("Released")
                                                    val runtime = movieJsonObject.optString("Runtime")
                                                    val genre = movieJsonObject.optString("Genre")
                                                    val actors = movieJsonObject.optString("Actors")
                                                    val plot = movieJsonObject.optString("Plot")
                                                    val poster = movieJsonObject.optString("Poster")
                                                    val imdbRating = movieJsonObject.optString("imdbRating")
                                                    val imdbVotes = movieJsonObject.optString("imdbVotes")
                                                    val type = movieJsonObject.optString("Type")

                                                    val filmeApi = FilmeApi(
                                                        title,
                                                        released,
                                                        runtime,
                                                        genre,
                                                        actors,
                                                        plot,
                                                        poster,
                                                        imdbRating,
                                                        imdbVotes,
                                                        imdbID,
                                                        type
                                                    )
                                                    Log.i("APP", " $filmeApi")
                                                    callback(filmeApi, null)
                                                    return
                                                }
                                            }
                                        }
                                    })
                                    return
                                }
                            }
                            callback(null, IOException("Film not found"))
                        } else {
                            callback(null, IOException("Film not found"))
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