package com.example.perludilindungi

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
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
    val cityNameData : ArrayList<String> = ArrayList<String>()
    val UNKNOWN_PROVINCE = "--Pilih Provinsi--"
    val UNKNOWN_CITY = "--Pilih Kota--"
    var selectedProvinceName : String = UNKNOWN_PROVINCE
    var selectedCityName : String = UNKNOWN_CITY


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

        viewModel.failMsg.observe(viewLifecycleOwner,  {
            Log.d("NEWS", "onCreateError: $it")
        })

        viewModel.getFaskes("DKI JAKARTA", "KOTA ADM. JAKARTA PUSAT")

        initSpinners()

        return(binding.root)
    }

    private fun initSpinners() {
        // Init province spinner
        provinceNameData.add(UNKNOWN_PROVINCE)
        initCityData()
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

        // get the province and add to spinner
        viewModel.getProvince()
        val spinnerProvinsi : Spinner = binding.spinnerProvinsi
        spinnerProvinsi.adapter = ArrayAdapter<String>(requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            provinceNameData)

        // get the city and add to spinner
        val spinnerKota : Spinner = binding.spinnerKota
        spinnerKota.adapter = ArrayAdapter<String>(requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            cityNameData)

        spinnerProvinsi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedProvinceName = parent?.getItemAtPosition(position).toString()
                selectProvinceAndCity()
                if (selectedProvinceName != UNKNOWN_PROVINCE) {
                    viewModel.getCity(selectedProvinceName)
                    viewModel.cityList.observe(viewLifecycleOwner, {
                            response ->
                        if (response != null) {
                            initCityData()
                            val cityData = response.results
                            for (city in cityData) {
                                cityNameData.add(city.value)
                            }
                        }else {
                            Toast.makeText(requireContext(), "City list not found", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }

        spinnerKota.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCityName = parent?.getItemAtPosition(position).toString()
                selectProvinceAndCity()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


    }

    private fun initFragment(faskesList: List<Faskes>) {
        faskesFragment = FaskesFragment.newInstance(faskesList)

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.faskes_page, faskesFragment)
            .commit()
    }

    private fun initCityData() {
        cityNameData.clear()
        cityNameData.add(UNKNOWN_CITY)
    }

    private fun selectProvinceAndCity() {
        if (selectedProvinceName == null) {
            selectedProvinceName = UNKNOWN_PROVINCE
        }
        if (selectedCityName == null) {
            selectedCityName = UNKNOWN_CITY
        }
        if (selectedCityName != UNKNOWN_CITY || selectedProvinceName != UNKNOWN_PROVINCE) {
            // init fragment
            viewModel.getFaskes(selectedProvinceName, selectedCityName)
        }
    }

}