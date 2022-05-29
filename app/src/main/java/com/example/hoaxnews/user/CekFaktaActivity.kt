package com.example.hoaxnews.user

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.hoaxnews.R
import com.example.hoaxnews.admin.HasilCekActivity
import com.example.hoaxnews.database.Laporan
import com.example.hoaxnews.databinding.ActivityCekFaktaBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_cek_fakta.*
import kotlinx.android.synthetic.main.activity_laporan.*
import kotlinx.android.synthetic.main.card_view_design.*

var hitung: Int = 0

class CekFaktaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCekFaktaBinding
    private lateinit var database: DatabaseReference
    private var judulSave = ""
    private var namaSave = ""
    private var linkSave = ""
    private var descSave = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCekFaktaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        count = 0

        val actionBar = supportActionBar
        actionBar!!.title = "Cek Fakta"
        actionBar.setDisplayHomeAsUpEnabled(true)

        if (hitung == 1) {
//            val stringUri = getIntent().getStringExtra("uri")
//            val uri = Uri.parse(stringUri)
//            binding.ivFotoFakta.setImageURI(uri)
//
//            binding.btnKirimFakta.setOnClickListener {
//                val judul = binding.etJudulFakta.text.toString()
//                val nama = binding.etNamaFakta.text.toString()
//                val link = binding.etLinkFakta.text.toString()
//                val desc = binding.etTeksFakta.text.toString()
//
//                database = FirebaseDatabase.getInstance("https://rpl-cc-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference()
//                val laporan = Laporan(stringUri, judul, nama, link, desc)
//                database.child("CekFakta").push().setValue(laporan).addOnSuccessListener {
//                    binding.ivFotoFakta.setImageResource(R.drawable.image)
//                    binding.etJudulFakta.text.clear()
//                    binding.etNamaFakta.text.clear()
//                    binding.etLinkFakta.text.clear()
//                    binding.etTeksFakta.text.clear()
//
//                    Toast.makeText(this, "Berhasil Disimpan", Toast.LENGTH_SHORT).show()
//                }.addOnFailureListener {
//                    Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
//                }
//
//                val bundle = Bundle()
//                bundle.putString("judul", etJudulFakta.text.toString())
//                bundle.putString("nama", etNamaFakta.text.toString())
//                bundle.putString("link", etLinkFakta.text.toString())
//                bundle.putString("desc", etTeksFakta.text.toString())
//
//                val intent = Intent(this, HasilCekActivity::class.java)
//                intent.putExtras(bundle)
//                intent.putExtra("uri", stringUri)
//                startActivity(intent)
//            }
        } else {
            binding.btnKirimFakta.setOnClickListener {
                Toast.makeText(this, "Lengkapi Form Cek Fakta", Toast.LENGTH_SHORT).show()
            }
        }
        binding.ivFotoFakta.setOnClickListener {
            pickImageGalery()
        }
        binding.btnLaporBerita.setOnClickListener {
            val intent = Intent(this, LaporanActivity::class.java)
            startActivity(intent)
        }
    }

    private fun pickImageGalery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        getResult.launch(intent)
    }

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            hitung = 1
            val URI = it.data?.data
            val stringUri = URI.toString()
            val intent = Intent(this, CekFaktaActivity::class.java)
            intent.putExtra("uri", stringUri)
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("judul", etJudulFakta.text.toString())
        outState.putString("nama", etNamaFakta.text.toString())
        outState.putString("link", etLinkFakta.text.toString())
        outState.putString("desc", etTeksFakta.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        judulSave = savedInstanceState.getString("judul").toString()
        namaSave = savedInstanceState.getString("nama").toString()
        linkSave = savedInstanceState.getString("link").toString()
        descSave = savedInstanceState.getString("desc").toString()

        binding.etJudulFakta.setText(judulSave)
        binding.etNamaFakta.setText(namaSave)
        binding.etLinkFakta.setText(linkSave)
        binding.etTeksFakta.setText(descSave)
    }
}