package com.example.perludilindungi.ui.faskes

import android.graphics.Movie
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.perludilindungi.MainViewModel
import com.example.perludilindungi.R
import com.example.perludilindungi.ViewModelFactory
import com.example.perludilindungi.databinding.FaskesListBinding
import com.example.perludilindungi.databinding.NewsBinding
import com.example.perludilindungi.model.FaskesResponse
import com.example.perludilindungi.network.RetrofitService
import com.example.perludilindungi.repository.Repository

class FaskesListActivity: AppCompatActivity() {
    private lateinit var faskesFragment: FaskesFragment

    private lateinit var faskesListBinding: FaskesListBinding
    private lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        faskesListBinding = FaskesListBinding.inflate(layoutInflater)
        setContentView(faskesListBinding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(Repository(retrofitService))
        )[MainViewModel::class.java]

        viewModel.faskesList.observe(this,  { response ->
            Log.d("FASKES", "onCreate: $response")
            if(response != null) {
                initFragment(response)
            } else {
                Toast.makeText(this@FaskesListActivity, "Province or city not found", Toast.LENGTH_SHORT)
            }
        })

        viewModel.failMsg.observe(this,  {
            Log.d("FASKES", "onCreateError: $it")
        })

        viewModel.getFaskes("DKI JAKARTA", "KOTA ADM. JAKARTA PUSAT")
    }

    private fun initFragment(faskesList: FaskesResponse) {
        faskesFragment = FaskesFragment.newInstance(faskesList)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.faskes_page, faskesFragment)
            .commit()
    }

}