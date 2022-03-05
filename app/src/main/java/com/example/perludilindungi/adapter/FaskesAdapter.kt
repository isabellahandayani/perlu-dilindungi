package com.example.perludilindungi.adapter

import android.content.Context
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.perludilindungi.databinding.FragmentFaskesItemBinding
import com.example.perludilindungi.model.Faskes

class FaskesAdapter(
    val values: List<Faskes>?
) : RecyclerView.Adapter<FaskesAdapter.FaskesViewHolder>() {

    private val rumahSakit = "RUMAH SAKIT"
    private val puskesmas = "PUSKESMAS"
    private val klinik = "KLINIK"

    private lateinit var listener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(pos: Int)
    }

    fun setOnItemClickListener(itemListener: onItemClickListener) {
        listener = itemListener
    }

    class FaskesViewHolder(val binding: FragmentFaskesItemBinding, val listener: onItemClickListener) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.newsCard.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaskesViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = FragmentFaskesItemBinding.inflate(inflater, parent, false)
        return FaskesViewHolder(binding, listener)
    }


    override fun getItemCount(): Int {
        return values?.size ?: 0
    }


    override fun onBindViewHolder(holder: FaskesViewHolder, position: Int) {
        val faskesData = values?.get(position)


        if (faskesData != null) {
            holder.binding.faskesAlamat.text = faskesData.alamat
            holder.binding.faskesTitle.text = faskesData.nama
            holder.binding.faskesPhone.text = faskesData.telp
            holder.binding.faskesCode.text = faskesData.kode
            holder.binding.faskesType.text = faskesData.jenis_faskes

            if (faskesData.jenis_faskes == rumahSakit) {
                holder.binding.faskesType.setTextColor(Color.parseColor("#FF6200EE"))
            }else if (faskesData.jenis_faskes == klinik) {
                holder.binding.faskesType.setTextColor(Color.parseColor("#7879F1"))
            }else if (faskesData.jenis_faskes == puskesmas) {
                holder.binding.faskesType.setTextColor(Color.parseColor("#EF5DA8"))
            }
        }
    }


}