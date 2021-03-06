package com.example.perludilindungi.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.perludilindungi.di.FaskesDao
import com.example.perludilindungi.model.Faskes

class FaskesRepository(private val faskesDao: FaskesDao) {

    val getData: LiveData<List<Faskes>> = faskesDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(faskes: Faskes) {
            faskesDao.insert(faskes)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(faskes: Faskes) {
        faskesDao.delete(faskes)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAll() {
        faskesDao.getAll()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun isExists(id: Int): Boolean {
        return faskesDao.getCount(id) > 0
    }
}
