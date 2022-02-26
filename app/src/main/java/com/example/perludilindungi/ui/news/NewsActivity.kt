package com.example.perludilindungi.ui.news

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.perludilindungi.MainViewModel
import com.example.perludilindungi.ViewModelFactory
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
        )[MainViewModel::class.java]

        binding.news.adapter = adapter
        adapter.setOnItemClickListener(object: NewsAdapter.onItemClickListener{
            override fun onItemClick(pos: Int) {
                intent = Intent(this@NewsActivity, NewsDetails::class.java)
                intent.putExtra("url", adapter.newsList[pos].guid)

                startActivity(intent)
            }

        })

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