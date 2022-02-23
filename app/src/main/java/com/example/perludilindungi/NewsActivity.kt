package com.example.perludilindungi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.perludilindungi.adapter.NewsAdapter
import com.example.perludilindungi.databinding.NewsBinding
import com.example.perludilindungi.network.RetrofitService
import com.example.perludilindungi.repository.Repository

class NewsActivity : AppCompatActivity() {
    private lateinit var binding: NewsBinding

    lateinit var viewModel: MainViewModel

    private val retrofitService = RetrofitService.getInstance()
    val adapter = NewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(Repository(retrofitService))
        ).get(MainViewModel::class.java)

        binding.news.adapter = adapter

        viewModel.list.observe(this, Observer { response ->
            Log.d("NEWS", "onCreate: $response")
            adapter.setNewsList(response)
        })

        viewModel.failMsg.observe(this, Observer {
            Log.d("NEWS", "onCreateError: $it")
        })

        viewModel.getNews()

    }
}