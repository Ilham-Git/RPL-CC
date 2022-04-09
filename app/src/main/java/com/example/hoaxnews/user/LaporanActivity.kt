package com.example.hoaxnews.user

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.hoaxnews.admin.HasilLaporanActivity
import com.example.hoaxnews.databinding.ActivityLaporanBinding
import kotlinx.android.synthetic.main.activity_laporan.*
import java.net.URI
import java.util.logging.Logger.global

var count: Int = 0

class LaporanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaporanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaporanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "Laporkan Berita"
        actionBar.setDisplayHomeAsUpEnabled(true)

       if (count == 1){
           val stringUri = getIntent().getStringExtra("uri")
           val uri = Uri.parse(stringUri)
           binding.ivFotoLapor.setImageURI(uri)

           binding.btnKirimLapor.setOnClickListener {
               val bundle = Bundle()
               bundle.putString("judul", etJudulLapor.text.toString())
               bundle.putString("nama", etNamaLapor.text.toString())
               bundle.putString("link", etLinkLapor.text.toString())
               bundle.putString("desc", etTeksLapor.text.toString())

               val intent = Intent(this, HasilLaporanActivity::class.java)
               intent.putExtras(bundle)
               intent.putExtra("uri", stringUri)
               startActivity(intent)
           }
       } else {
           binding.btnKirimLapor.setOnClickListener {
               Toast.makeText(this, "Lengkapi Form Pelaporan Berita", Toast.LENGTH_SHORT).show()
           }
       }
        
        binding.btnLaporBerita.setOnClickListener {
            val intent = Intent(this, LaporanActivity::class.java)
            startActivity(intent)
        }

        binding.btnCekFakta.setOnClickListener {
            val intent = Intent(this, CekFaktaActivity::class.java)
            startActivity(intent)
        }

        binding.ivFotoLapor.setOnClickListener {
            pickImageGalery()
        }
    }

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            count = 1
            val URI = it.data?.data
            val stringUri = URI.toString()
            val intent = Intent(this, LaporanActivity::class.java)
            intent.putExtra("uri", stringUri)
            startActivity(intent)
        }
    }

    private fun pickImageGalery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        getResult.launch(intent)
    }
}

