package com.example.perludilindungi.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "faskes")
data class FaskesDB (
    @PrimaryKey val id: Int
)