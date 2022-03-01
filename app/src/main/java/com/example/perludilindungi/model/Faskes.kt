package com.example.perludilindungi.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class FaskesResponse(
    val count_total: Int,
    val message: String,
    val data: List<Faskes>,
    val success: Boolean
)

data class Faskes(
    val alamat: String,
    val detail: List<Detail>,
    val jenis_faskes: String,
    val kelas_rs: String,
    val kode: String,
    val kota: String,
    val latitude: String,
    val longitude: String,
    val nama: String,
    val provinsi: String,
    val source_data: String,
    val status: String,
    val telp: String,
    val id: Int
)

data class Detail(
    val batal_vaksin: Int,
    val batal_vaksin_1: Int,
    val batal_vaksin_2: Int,
    val batch: String,
    val divaksin: Int,
    val divaksin_1: Int,
    val divaksin_2: Int,
    val id: Int,
    val kode: String,
    val pending_vaksin: Int,
    val pending_vaksin_1: Int,
    val pending_vaksin_2: Int,
    val tanggal: String
)