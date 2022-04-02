package com.example.hoaxnews.user

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.hoaxnews.admin.HasilCekActivity
import com.example.hoaxnews.databinding.ActivityCekFaktaBinding
import kotlinx.android.synthetic.main.activity_cek_fakta.*
import kotlinx.android.synthetic.main.card_view_design.*

class CekFaktaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCekFaktaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCekFaktaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "Cek Fakta"
        actionBar.setDisplayHomeAsUpEnabled(true)

        binding.ivFotoFakta.setOnClickListener{
            pickImageGalery()
        }
        binding.btnLaporBerita.setOnClickListener{
//            replaceFragment(FragmentLaporan())
            val intent = Intent(this, LaporanActivity::class.java)
            startActivity(intent)
        }
        binding.btnKirimFakta.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("judul", etJudulFakta.text.toString())
            bundle.putString("nama", etNamaFakta.text.toString())
            bundle.putString("link", etLinkFakta.text.toString())

            val intent = Intent(this, HasilCekActivity::class.java)
            intent.putExtras(bundle)
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
            val result = it.data?.data
            binding.ivFotoFakta.setImageURI(result)
        }
    }
}