package com.example.hoaxnews.database

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Laporan(
    val id_user: String,
    val image: String? = null,
    val title: String? = null,
    val name: String? = null,
    val link: String? = null,
    val desc: String? = null,
    val status: String? = null){
    constructor():this("","","","","","", "")
}
