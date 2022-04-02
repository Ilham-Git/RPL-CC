package com.example.hoaxnews.user//package com.example.hoaxnews
//
//import android.app.Activity
//import android.content.Intent
//import android.os.Bundle
//import android.view.View
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.fragment.app.Fragment
//import com.example.hoaxnews.databinding.FragmentLaporanBinding
//
//class FragmentLaporan : Fragment(R.layout.fragment_laporan) {
//
//    private lateinit var binding: FragmentLaporanBinding
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding = FragmentLaporanBinding.bind(view)
//        binding.ivFotoLapor.setOnClickListener{
//            pickImageGalery()
//        }
//    }
//
//    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//        if (it.resultCode == Activity.RESULT_OK) {
//            val result = it.data?.data
//            binding.ivFotoLapor.setImageURI(result)
//        }
//    }
//
//    private fun pickImageGalery() {
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        getResult.launch(intent)
//    }
//
//}