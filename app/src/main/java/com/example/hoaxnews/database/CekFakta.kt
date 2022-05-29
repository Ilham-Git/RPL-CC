package com.example.hoaxnews.database

data class CekFakta(
    val image: String? = null,
    val title: String? = null,
    val name: String? = null,
    val link: String? = null,
    val desc: String? = null,
    val status: String? = null){
    constructor():this("","","","","","")
}
