package com.example.perludilindungi.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.perludilindungi.model.Faskes

@Database(entities = [Faskes::class], version = 1, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {

    abstract fun faskesDao(): FaskesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "faskes_db"
                )
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}