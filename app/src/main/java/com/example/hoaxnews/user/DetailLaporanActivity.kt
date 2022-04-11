package com.example.hoaxnews.user

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hoaxnews.R
import com.example.hoaxnews.databinding.ActivityDetailLaporanBinding

class DetailLaporanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailLaporanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailLaporanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val stringUri = bundle?.getString("gambar")
        val uri = Uri.parse(stringUri)

        if (bundle != null) {
            binding.tvJudulDetailLapor.text = bundle.getString("judul")
            binding.ivGambarDetailLapor.setImageURI(uri)
            binding.tvDescDetailLapor.text = bundle.getString("desc")
        }
    }
}