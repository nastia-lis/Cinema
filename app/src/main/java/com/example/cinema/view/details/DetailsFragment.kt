package com.example.cinema.view.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cinema.databinding.FragmentDetailsBinding
import com.example.cinema.model.Movie
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_main_recycler_item.*
import kotlinx.android.synthetic.main.fragment_main_recycler_item.nameMovie
import kotlinx.android.synthetic.main.fragment_main_recycler_item.rating
import kotlinx.android.synthetic.main.fragment_main_recycler_item.released

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val BUNDLE_EXTRA = "movie"

        fun newInstance(bundle: Bundle) : DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Movie>(BUNDLE_EXTRA)?.let { movie ->
            movie.cinema.also { cinema ->
                nameMovie.text = cinema.movie
                released.text = cinema.released.toString()
                rating.text = cinema.rating.toString()
                description.text = movie.description
            }
        }
    }
}