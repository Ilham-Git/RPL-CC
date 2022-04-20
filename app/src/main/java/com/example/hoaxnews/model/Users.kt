package com.example.hoaxnews.model

class Users (val id : String,
             val nama : String?,
             val no_hp : String?,
             val lokasi : String?,
             val gambar : String?,
             val email : String){
    constructor():this("", "", "", " ", "","")
}