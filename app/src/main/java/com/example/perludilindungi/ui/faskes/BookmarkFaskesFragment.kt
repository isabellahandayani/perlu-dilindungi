package com.example.perludilindungi.ui.faskes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.viewModels
import com.example.perludilindungi.FaskesApplication
import com.example.perludilindungi.R
import com.example.perludilindungi.databinding.FragmentBookmarkFaskesBinding
import com.example.perludilindungi.model.Faskes
import com.example.perludilindungi.viewmodels.FaskesViewModel
import com.example.perludilindungi.viewmodels.FaskesViewModelFactory

class BookmarkFaskesFragment : Fragment() {
    private lateinit var binding: FragmentBookmarkFaskesBinding
    private lateinit var faskesFragment: FaskesFragment
    private val viewModel: FaskesViewModel by viewModels {
        FaskesViewModelFactory((requireActivity().application as FaskesApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentBookmarkFaskesBinding.inflate(inflater)
        viewModel.faskesList.observe(viewLifecycleOwner,  { response ->
            Log.d("FASKES", "onChange: $response")
            if(response != null) {
                initFragment(response)
            }
        })

        return(binding.root)
    }


    private fun initFragment(faskesList: List<Faskes>) {
        faskesFragment = FaskesFragment.newInstance(faskesList, 2)

        childFragmentManager
            .beginTransaction()
            .replace(R.id.list_faskes_page, faskesFragment)
            .commit()
    }

}