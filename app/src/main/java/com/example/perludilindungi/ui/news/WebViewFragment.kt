package com.example.perludilindungi.ui.news

import androidx.fragment.app.Fragment
import android.webkit.WebViewClient
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebSettings
import com.example.perludilindungi.databinding.NewsWebViewBinding


class WebViewFragment: Fragment() {
    private lateinit var binding: NewsWebViewBinding

    companion object {
        private var url: String? = null
        @JvmStatic
        fun newInstance(data: String) : WebViewFragment {
            val webViewFragment = WebViewFragment()
            url = data
            return webViewFragment
        }
        fun get(): String? {
            return url
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NewsWebViewBinding.inflate(layoutInflater)

        binding.newsWeb.webViewClient = WebViewClient()
        binding.newsWeb.loadUrl(get()!!)

        val settings: WebSettings = binding.newsWeb.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true

        return binding.root
    }
}