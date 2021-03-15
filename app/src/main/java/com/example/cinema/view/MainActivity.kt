package com.example.cinema.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cinema.R
import com.example.cinema.databinding.MainActivityBinding
import com.example.cinema.view.main.FragmentMain

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, FragmentMain.newInstance())
                    .commitNow()
        }
    }
}