package com.example.perludilindungi.ui.faskes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perludilindungi.R
import com.example.perludilindungi.adapter.FaskesAdapter
import com.example.perludilindungi.databinding.FragmentFaskesListBinding
import com.example.perludilindungi.model.Faskes

/**
 * A fragment representing a list of Items.
 */
class FaskesFragment : Fragment() {

    private lateinit var binding: FragmentFaskesListBinding
    private lateinit  var faskesAdapter: FaskesAdapter

    companion object {
        var faskesData: List<Faskes>? = null

        fun newInstance(dataset: List<Faskes>) : FaskesFragment {
            val faskesFragment = FaskesFragment()
            faskesData = dataset
            return faskesFragment
        }

        fun get(): List<Faskes>? {
            return faskesData
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(this.context)
        binding = FragmentFaskesListBinding.inflate(inflater)

        faskesAdapter = FaskesAdapter(get())
        binding.list.adapter = faskesAdapter

        faskesAdapter.setOnItemClickListener(object: FaskesAdapter.onItemClickListener{
            override fun onItemClick(pos: Int) {
                val detailFragment = DetailFaskesFragment.newInstance(faskesAdapter.values?.get(pos))
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_fragment, detailFragment)
                    .addToBackStack(null)
                    .commit()
            }

        })
        binding.list.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
        }

        return(binding.root)
    }


}

