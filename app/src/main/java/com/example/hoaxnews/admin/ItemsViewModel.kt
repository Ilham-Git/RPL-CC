package com.example.hoaxnews.admin

import android.net.Uri


data class ItemsViewModel(val image: Uri, val title: String, val name: String, val link: String, val desc: String) {
}