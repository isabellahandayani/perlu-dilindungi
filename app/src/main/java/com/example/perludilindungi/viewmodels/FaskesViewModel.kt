package com.example.perludilindungi.viewmodels

import androidx.lifecycle.*
import com.example.perludilindungi.model.FaskesDB
import com.example.perludilindungi.repository.FaskesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FaskesViewModel(private val repository: FaskesRepository)  : ViewModel() {

    private val faskesList: LiveData<List<FaskesDB>> = repository.getData


    fun insert(id:FaskesDB) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(id)
        }
    }

    fun delete(id: FaskesDB) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.delete(id)
            } catch (e: Exception) {

            }
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