package com.example.perludilindungi.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class FaskesResponse(
    val count_total: Int,
    val message: String,
    val data: List<Faskes>,
    val success: Boolean
)

@Entity(tableName = "faskes")
data class Faskes (
    var alamat: String,
    @Ignore var detail: List<Detail>? = null,
    var jenis_faskes: String,
    var kelas_rs: String,
    var kode: String,
    var kota: String,
    var latitude: String,
    var longitude: String,
    var nama: String,
    var provinsi: String,
    var source_data: String,
    var status: String,
    var telp: String,
    @PrimaryKey var id: Int
) {
    constructor() : this("", null, "", "", "", "", "", "","", "", "", "", "", 0 )
}

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