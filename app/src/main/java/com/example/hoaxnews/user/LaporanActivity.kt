package com.example.hoaxnews.user

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.hoaxnews.R
import com.example.hoaxnews.admin.HasilLaporanActivity
import com.example.hoaxnews.admin.ItemsViewModel
import com.example.hoaxnews.database.Laporan
import com.example.hoaxnews.databinding.ActivityLaporanBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_cek_fakta.*
import kotlinx.android.synthetic.main.activity_laporan.*
import java.net.URI
import java.util.logging.Logger.global

var count: Int = 0

class LaporanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaporanBinding
    private lateinit var database: DatabaseReference
    private var judulSave = ""
    private var namaSave = ""
    private var linkSave = ""
    private var descSave = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaporanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hitung = 0

        val actionBar = supportActionBar
        actionBar!!.title = "Laporkan Berita"
        actionBar.setDisplayHomeAsUpEnabled(true)

       if (count == 1){
//           val stringUri = getIntent().getStringExtra("uri")
//           val uri = Uri.parse(stringUri)
//           binding.ivFotoLapor.setImageURI(uri)
//
//           binding.btnKirimLapor.setOnClickListener {
//               val judul = binding.etJudulLapor.text.toString()
//               val nama = binding.etNamaLapor.text.toString()
//               val link = binding.etLinkLapor.text.toString()
//               val desc = binding.etTeksLapor.text.toString()
//
//               val bundle = Bundle()
//               bundle.putString("judul", judul)
//               bundle.putString("nama", nama)
//               bundle.putString("link", link)
//               bundle.putString("desc", desc)
//
//               database = FirebaseDatabase.getInstance("https://rpl-cc-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference()
//               val laporan = Laporan(stringUri, judul, nama, link, desc)
//               database.child("Laporan").push().setValue(laporan).addOnSuccessListener {
//                   binding.ivFotoLapor.setImageResource(R.drawable.image)
//                   binding.etJudulLapor.text.clear()
//                   binding.etNamaLapor.text.clear()
//                   binding.etLinkLapor.text.clear()
//                   binding.etTeksLapor.text.clear()
//
//                   Toast.makeText(this, "Berhasil Disimpan", Toast.LENGTH_SHORT).show()
//               }.addOnFailureListener {
//                   Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
//               }
//
//               val intent = Intent(this, HasilLaporanActivity::class.java)
//               intent.putExtras(bundle)
//               intent.putExtra("uri", stringUri)
//               startActivity(intent)
//           }
       } else {
           binding.btnKirimLapor.setOnClickListener {
               Toast.makeText(this, "Lengkapi Form Pelaporan Berita", Toast.LENGTH_SHORT).show()
           }
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("judul", etJudulLapor.text.toString())
        outState.putString("nama", etNamaLapor.text.toString())
        outState.putString("link", etLinkLapor.text.toString())
        outState.putString("desc", etTeksLapor.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        judulSave = savedInstanceState.getString("judul").toString()
        namaSave = savedInstanceState.getString("nama").toString()
        linkSave = savedInstanceState.getString("link").toString()
        descSave = savedInstanceState.getString("desc").toString()

        binding.etJudulLapor.setText(judulSave)
        binding.etNamaLapor.setText(namaSave)
        binding.etLinkLapor.setText(linkSave)
        binding.etTeksLapor.setText(descSave)
    }
}

