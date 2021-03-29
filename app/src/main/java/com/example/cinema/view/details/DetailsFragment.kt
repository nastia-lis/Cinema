package com.example.cinema.view.details

import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cinema.databinding.FragmentDetailsBinding
import com.example.cinema.model.MovieDTO
import com.example.cinema.model.Movie
import com.example.cinema.utils.showSnackBar
import com.example.cinema.viewmodel.AppState
import com.example.cinema.viewmodel.DetailsViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_main_recycler_item.nameMovie
import kotlinx.android.synthetic.main.fragment_main_recycler_item.rating
import kotlinx.android.synthetic.main.fragment_main_recycler_item.released
import okhttp3.*
import java.io.IOException
import kotlin.jvm.Throws

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieBundle: Movie

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
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
        viewModel.detailsLiveData.observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getMovieFromRemoteSource(movieBundle.cinema.id)
    }

    private fun renderData(appState: AppState) {
        when(appState) {
            is AppState.Success -> {
                fragmentDescription.visibility = View.VISIBLE
                loading.visibility = View.GONE
                setMovie(appState.movieFantastic[0])
            }
            is AppState.Loading -> {
                fragmentDescription.visibility = View.GONE
                loading.visibility = View.VISIBLE
            }
            is AppState.Error-> {
                fragmentDescription.visibility = View.VISIBLE
                loading.visibility = View.GONE
                fragmentDescription.showSnackBar("Error", "Reload", {viewModel.getMovieFromRemoteSource(movieBundle.cinema.id)})
            }
        }
    }

    private fun setMovie(movieDTO: Movie) {
        val movie = movieDTO.cinema
        nameMovie.text = movie.movie
        released.text = movie.released
        rating.text = movie.rating.toString()
        description.text = movieDTO.description
    }
}