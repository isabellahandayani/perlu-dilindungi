package com.example.perludilindungi.adapter

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ColorSpace
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
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors


class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){
    var newsList = mutableListOf<News>()
    private lateinit var newsListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(pos: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        newsListener = listener
    }

    class NewsViewHolder(val binding: NewsItemBinding, listener: onItemClickListener) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.newsCard.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    fun setNewsList(newsData: NewsResponse) {
        this.newsList = newsData.results.toMutableList()

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = NewsItemBinding.inflate(inflater, parent, false)
        return NewsViewHolder(binding, newsListener)
    }

    override fun getItemCount(): Int = newsList.size

    override fun onBindViewHolder(holder: NewsViewHolder, pos: Int) {
        val newsData = newsList[pos]
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
