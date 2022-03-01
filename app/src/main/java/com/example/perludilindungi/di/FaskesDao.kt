package com.example.perludilindungi.di

import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.perludilindungi.model.FaskesDB

@Dao
interface FaskesDao {
    @Query("SELECT * FROM faskes")
    fun getAll(): LiveData<List<FaskesDB>>

    @Query("SELECT * FROM faskes WHERE id=(:id)")
    fun getFaskes(id: Int): FaskesDB

    @Insert
    @Throws(SQLiteException::class)
    suspend fun insert(id: FaskesDB)

    @Delete
    @Throws(SQLiteException::class)
    suspend fun delete(id: FaskesDB)
}