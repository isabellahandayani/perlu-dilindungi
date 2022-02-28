package com.example.perludilindungi.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.perludilindungi.databinding.FragmentFaskesItemBinding
import com.example.perludilindungi.model.Faskes

class FaskesAdapter(
    private val values: List<Faskes>?
) : RecyclerView.Adapter<FaskesAdapter.FaskesViewHolder>() {

    class FaskesViewHolder(val binding: FragmentFaskesItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaskesViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = FragmentFaskesItemBinding.inflate(inflater, parent, false)
        return FaskesViewHolder(binding)
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
        }
    }


}