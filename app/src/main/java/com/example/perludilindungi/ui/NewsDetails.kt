package com.example.perludilindungi.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.example.perludilindungi.databinding.NewsWebViewBinding


class NewsDetails : AppCompatActivity() {
    private lateinit var binding: NewsWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = NewsWebViewBinding.inflate(layoutInflater)

        val url = this.intent.extras!!.getString("url")
        binding.newsWeb.webViewClient = WebViewClient()
        binding.newsWeb.loadUrl("https://www.google.com")

        val settings: WebSettings = binding.newsWeb.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true

        setContentView(binding.root)
    }
}