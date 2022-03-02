
package com.example.perludilindungi.ui.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perludilindungi.R
import com.example.perludilindungi.ViewModelFactory
import com.example.perludilindungi.adapter.NewsAdapter
import com.example.perludilindungi.databinding.NewsBinding
import com.example.perludilindungi.network.RetrofitService
import com.example.perludilindungi.repository.Repository
import com.example.perludilindungi.viewmodels.MainViewModel

class NewsFragment : Fragment() {
    private lateinit var binding: NewsBinding
    lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()
    val newsAdapter = NewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(this.context)
        binding = NewsBinding.inflate(inflater)

        binding.news.adapter = newsAdapter

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(Repository(retrofitService))
        )[MainViewModel::class.java]

        newsAdapter.setOnItemClickListener(object: NewsAdapter.onItemClickListener{
            override fun onItemClick(pos: Int) {
                val webView = WebViewFragment.newInstance(newsAdapter.newsList[pos].guid)
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_fragment, webView)
                    .addToBackStack(null)
                    .commit()
            }

        })

        viewModel.list.observe(viewLifecycleOwner,  { response ->
            Log.d("NEWS", "onCreate: $response")
            newsAdapter.setNewsList(response)
        })

        viewModel.failMsg.observe(viewLifecycleOwner,  {
            Log.d("NEWS", "onCreateError: $it")
        })

        viewModel.getNews()

        binding.news.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
        }

        return(binding.root)
    }

}