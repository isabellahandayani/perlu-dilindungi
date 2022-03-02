
package com.example.perludilindungi.ui.faskes

import android.database.SQLException
import android.database.sqlite.SQLiteConstraintException
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
import com.example.perludilindungi.viewmodels.FaskesViewModel
import com.example.perludilindungi.viewmodels.FaskesViewModelFactory
import java.lang.NullPointerException

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

        val faskes = get()!!

        try {
            displayFaskesData(faskes)
        }catch (e :NullPointerException) {
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
        }

        binding.btnBookmark.setOnClickListener {

            try {
                viewModel.insert(faskes)
                Toast.makeText(requireContext(), "Successfully bookmarked", Toast.LENGTH_SHORT).show()

            } catch (e: SQLiteConstraintException) {
                Toast.makeText(requireContext(), "Fail to bookmark", Toast.LENGTH_SHORT).show()
            }
        }


        return binding.root
    }

    private fun displayFaskesData(faskes : Faskes) {
        binding.namaFaskes.setText(faskes.nama)
        binding.kodeFaskes.setText(faskes.kode)
        binding.jenisFaskes.setText(faskes.jenis_faskes)
        binding.alamatFaskes.setText(faskes.alamat)
        binding.teleponFaskes.setText(faskes.telp)
        binding.statusFaskes.setText(faskes.status)
    }


}