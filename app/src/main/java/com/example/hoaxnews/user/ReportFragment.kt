package com.example.hoaxnews.user

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hoaxnews.R
import com.example.hoaxnews.database.Laporan
import com.example.hoaxnews.databinding.FragmentReportBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso


class ReportFragment : Fragment() {

    private lateinit var binding: FragmentReportBinding
    private lateinit var database: DatabaseReference
    lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    lateinit var ImageUri : Uri
    var count : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReportBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()
        firebaseUser = auth.currentUser!!
        database = FirebaseDatabase.getInstance("https://rpl-cc-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Laporan")

        count = 0

        // Button Lapor
        binding.btnKirimLapor.setOnClickListener {

            val id_laporan = database.push().key
            val judul = binding.etJudulLapor.text.toString()
            val nama = binding.etNamaLapor.text.toString()
            val link = binding.etLinkLapor.text.toString()
            val desc = binding.etTeksLapor.text.toString()

            // Validasi form
            if(count != 1){
                Toast.makeText(context,"Masukkan Foto!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(judul.isEmpty()){
                binding.etJudulLapor.error = "Masukkan judul!"
                binding.etJudulLapor.requestFocus()
                return@setOnClickListener
            }else if(nama.isEmpty()) {
                binding.etNamaLapor.error = "Masukkan nama!"
                binding.etNamaLapor.requestFocus()
                return@setOnClickListener
            } else if(link.isEmpty()) {
                binding.etLinkLapor.error = "Masukkan link!"
                binding.etLinkLapor.requestFocus()
                return@setOnClickListener
            } else if(desc.isEmpty()) {
                binding.etTeksLapor.error = "Masukkan Teks!"
                binding.etTeksLapor.requestFocus()
                return@setOnClickListener
            }

            val imageRef = FirebaseStorage.getInstance().reference.child("gambarLaporan/${id_laporan}")
            imageRef.putFile(ImageUri!!).addOnSuccessListener{
                val uriTask : Task<Uri> = it.storage.downloadUrl

                while (!uriTask.isSuccessful);
                val uploadedImageUrl = "${uriTask.result}"

                laporBerita(id_laporan, uploadedImageUrl, judul, nama, link, desc)
            }

        }

        // Button Gambar
        binding.ivFotoLapor.setOnClickListener {
            pickImageGalery()
        }

        // Button menuju lapor
        val fragmentFakta = FaktaFragment()

        binding.btnCekFakta.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, fragmentFakta, FaktaFragment::class.java.simpleName)
                    .commit()
            }
        }

        return binding.root
    }

    private fun laporBerita(id_laporan : String?,
                            uploadedImageUrl : String,
                            judul : String,
                            nama : String,
                            link : String,
                            desc : String){

        val status = "Sedang Di Proses"
        val id = firebaseUser.uid

        val laporan = Laporan(id, uploadedImageUrl, judul, nama, link, desc, status)


        if(id_laporan != null) {
            database.child(id_laporan).setValue(laporan).addOnSuccessListener {
                binding.ivFotoLapor.setImageResource(R.drawable.image)
                binding.etJudulLapor.text.clear()
                binding.etNamaLapor.text.clear()
                binding.etLinkLapor.text.clear()
                binding.etTeksLapor.text.clear()
                count = 0

                Toast.makeText(context, "Berhasil Disimpan", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun pickImageGalery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == Activity.RESULT_OK){

            count = 1
            ImageUri = data?.data!!

            Picasso.get()
                .load(ImageUri)
                .resize(200,100)
                .into(binding.ivFotoLapor)

        }
    }

}