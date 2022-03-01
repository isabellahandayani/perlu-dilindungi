package com.example.perludilindungi

import android.app.Application
import com.example.perludilindungi.di.AppDatabase
import com.example.perludilindungi.repository.FaskesRepository

class FaskesApplication : Application() {
        val database by lazy { AppDatabase.getDatabase(this) }
        val repository by lazy { FaskesRepository(database.faskesDao()) }
}