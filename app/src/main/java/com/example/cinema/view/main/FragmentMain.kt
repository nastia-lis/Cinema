package com.example.cinema.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cinema.R
import com.example.cinema.databinding.FragmentMainBinding
import com.example.cinema.model.Movie
import com.example.cinema.view.details.DetailsFragment
import com.example.cinema.viewmodel.AppState
import com.example.cinema.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.main_activity.view.*

class FragmentMain : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java) }

    private val adapterFantastic = MainFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(movie: Movie) {
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .add(R.id.container, DetailsFragment.newInstance(Bundle().apply { putParcelable(DetailsFragment.BUNDLE_EXTRA, movie) }))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })

    private val adapterComedy = MainFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(movie: Movie) {
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .add(R.id.container, DetailsFragment.newInstance(Bundle().apply { putParcelable(DetailsFragment.BUNDLE_EXTRA, movie) }))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewFantastic.adapter = adapterFantastic
        binding.recyclerViewComedy.adapter = adapterComedy
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it as AppState) })
        viewModel.getMovie()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        adapterFantastic.removeListener()
        adapterComedy.removeListener()
        super.onDestroy()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                loading.visibility = View.GONE
                adapterFantastic.setMovie(appState.movieFantastic)
                adapterComedy.setMovie(appState.movieComedy)
            }
            is AppState.Loading -> {
                loading.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                loading.visibility = View.GONE
                mainFragmentRoot.showSnackBar("Error", "Reload", { viewModel.getMovie() } )
            }
        }
    }

    companion object {
        fun newInstance() = FragmentMain()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movie: Movie)
    }

    private fun View.showSnackBar(
        text: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, text, length).setAction(actionText, action).show()
    }
}