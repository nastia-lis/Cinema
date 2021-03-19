package com.example.cinema

import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.cinema.model.MovieDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class MovieLoader(private val listener: MovieLoaderListener, private val id: Int) {

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadMovie() {
        try {
            val uri =
                URL("https://api.tmdb.org/3/movie/${id}?api_key=ba94ef13c60c86a4f3cc51f6e1cb74b5")
            val handler = Handler()
            Thread(Runnable {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.addRequestProperty("api_key", "ba94ef13c60c86a4f3cc51f6e1cb74b5")
                    urlConnection.readTimeout = 1000
                    val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val movieDTO: MovieDTO =
                        Gson().fromJson(getLines(bufferedReader), MovieDTO::class.java)
                    handler.post { listener.onLoaded(movieDTO) }
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                } finally {
                    urlConnection.disconnect()
                }
            }).start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
            listener.onFailed(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    interface MovieLoaderListener {
        fun onLoaded(movieDTO: MovieDTO)
        fun onFailed(throwable: Throwable)
    }
}