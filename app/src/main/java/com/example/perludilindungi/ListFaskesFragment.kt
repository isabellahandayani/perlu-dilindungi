package com.example.perludilindungi

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.perludilindungi.databinding.FragmentListFaskesBinding
import com.example.perludilindungi.model.Faskes
import com.example.perludilindungi.model.Province
import com.example.perludilindungi.network.RetrofitService
import com.example.perludilindungi.repository.Repository
import com.example.perludilindungi.ui.faskes.FaskesFragment

class ListFaskesFragment : Fragment() {
    private lateinit var binding: FragmentListFaskesBinding
    lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var faskesFragment: FaskesFragment
    val provinceNameData : ArrayList<String> = ArrayList<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentListFaskesBinding.inflate(inflater)


        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(Repository(retrofitService))
        )[MainViewModel::class.java]


        viewModel.faskesList.observe(viewLifecycleOwner,  { response ->
            Log.d("FASKES", "onCreate: $response")
            if(response != null) {
                initFragment(response.data)
            } else {
                Toast.makeText(requireContext(), "Province or city not found", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.provinceList.observe(viewLifecycleOwner, {
            response ->
            if (response != null) {
                val provinceData = response.results
                for (province in provinceData) {
                    provinceNameData.add(province.key)
                }
            } else {
                Toast.makeText(requireContext(), "Province list not found", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.failMsg.observe(viewLifecycleOwner,  {
            Log.d("NEWS", "onCreateError: $it")
        })

        viewModel.getFaskes("DKI JAKARTA", "KOTA ADM. JAKARTA PUSAT")

        // get the province and add to spinner
        viewModel.getProvince()
        val spinner : Spinner = binding.spinnerProvinsi
        spinner.adapter = ArrayAdapter<String>(requireContext(),
        R.layout.support_simple_spinner_dropdown_item,
        provinceNameData)


        return(binding.root)
    }

    private fun initFragment(faskesList: List<Faskes>) {
        faskesFragment = FaskesFragment.newInstance(faskesList)

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.faskes_page, faskesFragment)
            .commit()
    }

}