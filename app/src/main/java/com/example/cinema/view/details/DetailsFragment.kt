package com.example.cinema.view.details

import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.cinema.databinding.FragmentDetailsBinding
import com.example.cinema.model.MovieDTO
import com.example.cinema.model.Movie
import com.example.cinema.viewmodel.AppState
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
        getMovie()
    }

    private fun getMovie() {
        fragmentDescription.visibility = View.GONE
        loading.visibility = View.VISIBLE

        val client = OkHttpClient()
        val builder: Request.Builder = Request.Builder()
        builder.url("https://api.tmdb.org/3/movie/${movieBundle.cinema.id}?api_key=ba94ef13c60c86a4f3cc51f6e1cb74b5")
        val request: Request = builder.build()
        val call: Call = client.newCall(request)
        call.enqueue(object : Callback {
            val handler: Handler = Handler()
            @Throws(IOException::class)
            override fun onResponse(call: Call?, response: Response) {
                val serverResponse: String? = response.body()?.string()
                if (response.isSuccessful && serverResponse != null) {
                    handler.post {
                        renderData(Gson().fromJson(serverResponse, MovieDTO::class.java))
                    }
                } else {
                    AppState.Error(Throwable("Error Server"))
                }
            }
            override fun onFailure(call: Call?, e: IOException?) {
                AppState.Error(Throwable("Error Server"))
            }
        })
    }

    private fun renderData(movieDTO: MovieDTO) {
        fragmentDescription.visibility = View.VISIBLE
        loading.visibility = View.GONE

        if (movieDTO.title.isNullOrEmpty() || movieDTO.release_date.isNullOrEmpty() || movieDTO.vote_average == null || movieDTO.overview.isNullOrEmpty()) {
            AppState.Error(Throwable("Error Server"))
        } else {
            nameMovie.text = movieDTO.title
            released.text = movieDTO.release_date
            rating.text = movieDTO.vote_average.toString()
            description.text = movieDTO.overview
        }
    }
}