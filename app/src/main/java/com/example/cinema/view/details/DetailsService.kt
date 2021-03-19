package com.example.cinema.view.details

import android.app.IntentService
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.cinema.BuildConfig
import com.example.cinema.model.MovieDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val ID = "ID"
private const val REQUEST_GET = "GET"
private const val REQUEST_TIMEOUT = 10000
private const val REQUEST_API_KEY = "API_KEY"

class DetailsService(name: String = "DetailService") : IntentService(name) {

    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)
    
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            onEmptyIntent()
        } else {
            val id = intent.getIntExtra(ID, 0)
            if (id == 0) {
                onEmptyData()
            } else {
                loadMovie(id)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadMovie(id: Int) {
        try {
            val uri = URL("https://api.tmdb.org/3/movie/${id}?api_key=ba94ef13c60c86a4f3cc51f6e1cb74b5")
            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.apply {
                    requestMethod = REQUEST_GET
                    readTimeout = REQUEST_TIMEOUT
                    addRequestProperty(REQUEST_API_KEY, BuildConfig.MOVIE_API_KEY)
                }
                val movieDTO: MovieDTO = Gson().fromJson(getLines(BufferedReader(InputStreamReader(urlConnection.inputStream))), MovieDTO::class.java)
            onResponse(movieDTO)
        } catch (e: Exception) {
            onErrorRequest(e.message ?: "Empty error")
        } finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            onMalformedURL()
        }
    }

    private fun onMalformedURL() {
        putLoadResult(DETAILS_URL_MALFORMED_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)
    }

    private fun onErrorRequest(error: String) {
        putLoadResult(DETAILS_REQUEST_ERROR_EXTRA)
        broadcastIntent.putExtra(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA, error)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onResponse(movieDTO: MovieDTO) {
        if (movieDTO == null) {
            onEmptyResponse()
        } else {
            onSuccessResponse(movieDTO.title, movieDTO.release_date, movieDTO.vote_average, movieDTO.overview)
        }
    }

    private fun onSuccessResponse(title: String?, releaseDate: String?, voteAverage: Double?, overview: String?) {
        putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putExtra(DETAILS_TITLE, title)
        broadcastIntent.putExtra(DETAILS_RELEASED, releaseDate)
        broadcastIntent.putExtra(DETAILS_RATING, voteAverage)
        broadcastIntent.putExtra(DETAILS_OVERVIEW, overview)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyResponse() {
        putLoadResult(DETAILS_RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(bufferedReader: BufferedReader): String {
        return bufferedReader.lines().collect(Collectors.joining("\n"))
    }

    private fun onEmptyData() {
        putLoadResult(DETAILS_DATA_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyIntent() {
        putLoadResult(DETAILS_INTENT_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }
}