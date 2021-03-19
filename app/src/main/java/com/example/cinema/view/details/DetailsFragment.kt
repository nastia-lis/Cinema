package com.example.cinema.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.cinema.MovieLoader
import com.example.cinema.databinding.FragmentDetailsBinding
import com.example.cinema.model.Cinema
import com.example.cinema.model.MovieDTO
import com.example.cinema.model.Movie
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_main_recycler_item.nameMovie
import kotlinx.android.synthetic.main.fragment_main_recycler_item.rating
import kotlinx.android.synthetic.main.fragment_main_recycler_item.released
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
const val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"
const val DETAILS_INTENT_EMPTY_EXTRA = "INTENT IS EMPTY"
const val DETAILS_DATA_EMPTY_EXTRA = "DATA IS EMPTY"
const val DETAILS_RESPONSE_EMPTY_EXTRA = "RESPONSE IS EMPTY"
const val DETAILS_REQUEST_ERROR_EXTRA = "REQUEST ERROR"
const val DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = "REQUEST ERROR MESSAGE"
const val DETAILS_URL_MALFORMED_EXTRA = "URL MALFORMED"
const val DETAILS_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"
const val DETAILS_TITLE = "TITLE"
const val DETAILS_RELEASED = "RELEASED"
const val DETAILS_RATING = "RATING"
const val DETAILS_OVERVIEW = "OVERAGE"
private const val RATING_INVALID = -100.0
private const val PROCESS_ERROR = "Обработка ошибки"

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieBundle: Movie

    private val onLoaderListener: MovieLoader.MovieLoaderListener =
        object : MovieLoader.MovieLoaderListener {
            override fun onLoaded(movieDTO: MovieDTO) {
                displayMovie(movieDTO)
            }

            override fun onFailed(throwable: Throwable) {
            }
        }

    private val loadResultReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                DETAILS_INTENT_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_DATA_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_URL_MALFORMED_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_SUCCESS_EXTRA -> renderData(
                    MovieDTO(
                        intent.getStringExtra(DETAILS_TITLE),
                        intent.getStringExtra(DETAILS_RELEASED),
                        intent.getDoubleExtra(DETAILS_RATING, RATING_INVALID),
                        intent.getStringExtra(DETAILS_OVERVIEW)
                    )
                )
                else -> TODO(PROCESS_ERROR)
            }
        }
    }

    companion object {
        const val BUNDLE_EXTRA = "movie"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            LocalBroadcastManager.getInstance(it).registerReceiver(
                loadResultReceiver, IntentFilter(
                    DETAILS_INTENT_FILTER
                )
            )
        }
    }

    override fun onDestroy() {
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultReceiver)
        }
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Movie()
//        binding.fragmentDescription.visibility = View.GONE
//        binding.loading.visibility = View.VISIBLE
//        val loader = MovieLoader(onLoaderListener, movieBundle.cinema.id)
//        loader.loadMovie()
        getMovie()
    }

    private fun getMovie() {
        binding.fragmentDescription.visibility = View.GONE
        binding.loading.visibility = View.VISIBLE
        context?.let {
            it.startService(Intent(it, DetailsService::class.java).apply {
                putExtra(ID, movieBundle.cinema.id)
            })
        }
    }

    private fun displayMovie(movieDTO: MovieDTO) {
        with(binding) {
            fragmentDescription.visibility = View.VISIBLE
            loading.visibility = View.GONE
            nameMovie.text = movieDTO.title
            released.text = movieDTO.release_date
            rating.text = movieDTO.vote_average.toString()
            description.text = movieDTO.overview
        }
    }

    private fun renderData(movieDTO: MovieDTO) {
        fragmentDescription.visibility = View.VISIBLE
        loading.visibility = View.GONE

        val title = movieDTO.title
        val released = movieDTO.release_date
        val rating = movieDTO.vote_average
        val overview = movieDTO.overview

        if (title == null || released == null || rating == RATING_INVALID || overview == null) {
            TODO(PROCESS_ERROR)
        } else {
            binding.nameMovie.text = movieBundle.cinema.title
            binding.released.text = movieBundle.cinema.released.toString()
            binding.rating.text = movieBundle.cinema.rating.toString()
            binding.description.text = movieBundle.description
        }
    }
}