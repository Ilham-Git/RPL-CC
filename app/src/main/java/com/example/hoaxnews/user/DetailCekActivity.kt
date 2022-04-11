package com.example.hoaxnews.user

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hoaxnews.R
import com.example.hoaxnews.databinding.ActivityDetailCekBinding

class DetailCekActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailCekBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCekBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val stringUri = bundle?.getString("gambar")
        val uri = Uri.parse(stringUri)

        if (bundle != null) {
            binding.tvJudulDetailCek.text = bundle.getString("judul")
            binding.ivGambarDetailCek.setImageURI(uri)
            binding.tvDescDetailCek.text = bundle.getString("desc")
        }
    }
}