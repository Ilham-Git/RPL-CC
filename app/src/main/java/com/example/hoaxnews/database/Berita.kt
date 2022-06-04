package com.example.hoaxnews.database

data class Berita (
    val gambar: String?,
    val judul: String?,
    val link: String?,
    val tanggal_buat: String?){
    constructor():this("","","","")
}
