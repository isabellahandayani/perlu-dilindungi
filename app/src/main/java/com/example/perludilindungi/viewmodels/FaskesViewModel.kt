package com.example.perludilindungi.viewmodels

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.*
import com.example.perludilindungi.model.Faskes
import com.example.perludilindungi.repository.FaskesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.jvm.Throws

class FaskesViewModel(private val repository: FaskesRepository)  : ViewModel() {

    val faskesList: LiveData<List<Faskes>> = repository.getData
    val cnt: Int = repository.cnt
    val exist: Boolean = repository.exist


    @Throws(SQLiteConstraintException::class)
    fun insert(faskes:Faskes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(faskes)
        }
    }

    fun delete(faskes: Faskes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(faskes)
        }
    }

    fun isExists(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.isExists(id)
        }
    }

    fun count(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.count(id)
        }
    }
}

class FaskesViewModelFactory(val repository: FaskesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FaskesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FaskesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}