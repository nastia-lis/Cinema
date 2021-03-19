package com.example.cinema.view.details

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

    companion object {
        const val BUNDLE_EXTRA = "movie"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
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
        binding.fragmentDescription.visibility = View.GONE
        binding.loading.visibility = View.VISIBLE
        val loader = MovieLoader(onLoaderListener, movieBundle.cinema.id)
        loader.loadMovie()
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
}