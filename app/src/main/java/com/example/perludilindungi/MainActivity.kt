package com.example.perludilindungi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.perludilindungi.databinding.ActivityMainBinding
import com.example.perludilindungi.ui.faskes.FaskesListActivity
import com.example.perludilindungi.ui.news.NewsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
//    private lateinit var listIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.newsButton.setOnClickListener {
//            listIntent = Intent(this, NewsActivity::class.java)
//            startActivity(listIntent)
//        }
//
//        binding.searchFaskes.setOnClickListener{
//            listIntent = Intent(this, FaskesListActivity::class.java)
//            startActivity(listIntent)
//        }
        val bottomNavigationView = binding.bottomNavigationView
        val navController = findNavController(R.id.nav_fragment)
        bottomNavigationView.setupWithNavController(navController)
    }
}