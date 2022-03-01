package com.example.perludilindungi.ui.faskes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.perludilindungi.R
import com.example.perludilindungi.databinding.FragmentBookmarkFaskesBinding
import com.example.perludilindungi.model.Faskes

class BookmarkFaskesFragment : Fragment() {
    private lateinit var binding: FragmentBookmarkFaskesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentBookmarkFaskesBinding.inflate(inflater)

        return(binding.root)
    }


}