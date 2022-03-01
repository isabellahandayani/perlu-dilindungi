package com.example.perludilindungi.repository

import android.database.sqlite.SQLiteException
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.perludilindungi.di.FaskesDao
import com.example.perludilindungi.model.FaskesDB

class FaskesRepository(private val faskesDao: FaskesDao) {

    val getData: LiveData<List<FaskesDB>> = faskesDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(id: FaskesDB) {
            faskesDao.insert(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(id: FaskesDB) {
        faskesDao.delete(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAll() {
        faskesDao.getAll()
    }
}
