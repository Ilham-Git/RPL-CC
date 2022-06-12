package com.example.hoaxnews.database

data class Berita (
    val image: String?,
    val judul: String?,
    val link: String?,
    val desc: String?,
    val tanggal_buat: String?){
    constructor():this("","","","","")
}
