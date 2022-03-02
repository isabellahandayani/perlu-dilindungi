package com.example.perludilindungi.di

import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.perludilindungi.model.Faskes

@Dao
interface FaskesDao {
    @Query("SELECT * FROM faskes")
    fun getAll(): LiveData<List<Faskes>>

    @Query("SELECT * FROM faskes WHERE id=(:id)")
    fun getFaskes(id: Int): Faskes

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @Throws(SQLiteException::class)
    suspend fun insert(faskes: Faskes)

    @Delete
    @Throws(SQLiteException::class)
    suspend fun delete(faskes: Faskes)
}