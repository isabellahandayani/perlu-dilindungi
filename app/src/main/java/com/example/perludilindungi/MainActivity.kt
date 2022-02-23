package com.example.perludilindungi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.perludilindungi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.newsButton.setOnClickListener { launchNews() }
    }

    private fun launchNews() {
        listIntent = Intent(this, NewsActivity::class.java)
        startActivity(listIntent)
    }
}