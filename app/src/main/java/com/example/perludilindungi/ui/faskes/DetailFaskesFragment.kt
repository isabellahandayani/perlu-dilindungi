
package com.example.perludilindungi.ui.faskes

import android.database.sqlite.SQLiteException
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.perludilindungi.FaskesApplication
import com.example.perludilindungi.databinding.FragmentDetailFaskesBinding
import com.example.perludilindungi.model.Faskes
import com.example.perludilindungi.model.FaskesDB
import com.example.perludilindungi.viewmodels.FaskesViewModel
import com.example.perludilindungi.viewmodels.FaskesViewModelFactory

class DetailFaskesFragment : Fragment() {
    private lateinit var binding: FragmentDetailFaskesBinding
    private val viewModel: FaskesViewModel by viewModels {
        FaskesViewModelFactory((requireActivity().application as FaskesApplication).repository)
    }

    companion object {
        private var data: Faskes? = null
        @JvmStatic
        fun newInstance(faskes: Faskes?): DetailFaskesFragment {
            val detailFragment = DetailFaskesFragment()
            data = faskes
            return detailFragment
        }
        fun get(): Faskes? {
            return data
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailFaskesBinding.inflate(inflater)

        binding.btnBookmark.setOnClickListener {
            val faskesDB = FaskesDB(get()!!.id)

            try {
                viewModel.insert(faskesDB)
                Toast.makeText(requireContext(), "Successfully bookmarked", Toast.LENGTH_SHORT).show()

            } catch (e: SQLiteException) {
                Toast.makeText(requireContext(), "Fail to bookmark", Toast.LENGTH_SHORT).show()
            }


        }


        return binding.root
    }


}