package com.example.perludilindungi.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.perludilindungi.News
import com.example.perludilindungi.NewsResponse
import com.example.perludilindungi.databinding.NewsItemBinding
import java.text.DateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors


class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    var news = mutableListOf<News>()

    class NewsViewHolder(val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root)

    fun setNewsList(newsData: NewsResponse) {
        this.news = newsData.results.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = NewsItemBinding.inflate(inflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int = news.size

    override fun onBindViewHolder(holder: NewsViewHolder, pos: Int) {
        val newsData = news[pos]
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap?
        executor.execute {

            val imageURL = newsData.enclosure._url

            try {
                val imageInput = URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(imageInput)

                handler.post {
                    holder.binding.newsImage.setImageBitmap(image)
                }
            }

            catch (e: Exception) {
                e.printStackTrace()
            }
        }

        holder.binding.newsTitle.text = newsData.title
        holder.binding.newsDate.text = newsData.pubDate.take(16)
    }
}