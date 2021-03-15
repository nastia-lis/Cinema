package com.example.cinema.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.databinding.FragmentMainRecyclerItemBinding
import com.example.cinema.model.Movie

class MainFragmentAdapter(private var onItemViewClickListener: FragmentMain.OnItemViewClickListener?) : RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private lateinit var binding: FragmentMainRecyclerItemBinding

    private var movieData: List<Movie> = listOf()

    fun setMovie(data: List<Movie>) {
        movieData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        binding = FragmentMainRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(movieData[position])
    }

    override fun getItemCount(): Int {
        return movieData.size
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            binding.nameMovie.text =
                movie.cinema.movie
            binding.released.text =
                movie.cinema.released.toString()
            binding.rating.text =
                movie.cinema.rating.toString()
            binding.itemContainer.setOnClickListener {
                onItemViewClickListener?.onItemViewClick(movie)
            }
        }
    }

    fun removeListener() {
        onItemViewClickListener = null
    }
}