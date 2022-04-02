package com.example.hoaxnews.user

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.hoaxnews.admin.HasilLaporanActivity
import com.example.hoaxnews.databinding.ActivityLaporanBinding
import kotlinx.android.synthetic.main.activity_laporan.*

class LaporanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaporanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaporanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "Laporkan Berita"
        actionBar.setDisplayHomeAsUpEnabled(true)

        binding.btnLaporBerita.setOnClickListener{
//            replaceFragment(FragmentLaporan())
        }
        binding.btnCekFakta.setOnClickListener {
//            replaceFragment(FragmentCekFakta())
            val intent = Intent(this, CekFaktaActivity::class.java)
            startActivity(intent)
        }
        binding.ivFotoLapor.setOnClickListener{
            pickImageGalery()
        }
        binding.btnKirimLapor.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("judul", etJudulLapor.text.toString())
            bundle.putString("nama", etNamaLapor.text.toString())
            bundle.putString("link", etLinkLapor.text.toString())
            bundle.putString("desc", etTeksLapor.text.toString())

            val intent = Intent(this, HasilLaporanActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val result = it.data?.data
            binding.ivFotoLapor.setImageURI(result)
        }
    }

    private fun pickImageGalery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        getResult.launch(intent)
    }
//        binding.btnLaporBerita.setOnClickListener{
//            replaceFragment(FragmentLaporan())
//        }
//        binding.btnCekFakta.setOnClickListener {
//            replaceFragment(FragmentCekFakta())
//        }
//
//    }
//
//    private fun replaceFragment(fragment: Fragment) {
//        val fragmentManager = supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
//        fragmentTransaction.commit()
//    }
}

