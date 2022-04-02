package com.example.hoaxnews.user//package com.example.hoaxnews
//
//import android.app.Activity
//import android.content.Intent
//import android.os.Bundle
//import android.view.View
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.fragment.app.Fragment
//import com.example.hoaxnews.databinding.FragmentCekfaktaBinding
//
//class FragmentCekFakta : Fragment(R.layout.fragment_cekfakta) {
//
//    private lateinit var binding: FragmentCekfaktaBinding
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding = FragmentCekfaktaBinding.bind(view)
//        binding.ivFotoFakta.setOnClickListener{
//            pickImageGalery()
//        }
//    }
//
//    private fun pickImageGalery() {
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        getResult.launch(intent)
//    }
//
//    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//        if (it.resultCode == Activity.RESULT_OK){
//            val result = it.data?.data
//            binding.ivFotoFakta.setImageURI(result)
//        }
//    }
//}