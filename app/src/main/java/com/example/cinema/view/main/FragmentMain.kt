package com.example.cinema.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cinema.databinding.FragmentMainBinding
import com.example.cinema.viewmodel.AppState
import com.example.cinema.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main.*

class FragmentMain : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private val adapter = MainFragmentAdapter()
    private val adapterComedy = MainFragmentAdapter()
    private var isDataSetFantastic: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewFantastic.adapter = adapter
        binding.recyclerViewComedy.adapter = adapterComedy
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it as AppState) })
        viewModel.getMovie()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                loading.visibility = View.GONE
                adapter.setMovie(appState.movieFantastic)
                adapterComedy.setMovie(appState.movieComedy)
            }
            is AppState.Loading -> {
                loading.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                loading.visibility = View.GONE
                Snackbar
                    .make(recyclerViewFantastic, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getMovie() }
                    .show()
            }
        }
    }

    companion object {
        fun newInstance() = FragmentMain()
    }
}