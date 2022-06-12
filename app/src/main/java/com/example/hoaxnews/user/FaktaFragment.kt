package com.example.hoaxnews.user

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hoaxnews.R
import com.example.hoaxnews.database.CekFakta
import com.example.hoaxnews.database.Laporan
import com.example.hoaxnews.databinding.FragmentFaktaBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso


class FaktaFragment : Fragment() {

    private lateinit var binding: FragmentFaktaBinding
    private lateinit var database: DatabaseReference
    lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var progresDialog: ProgressDialog
    lateinit var ImageUri : Uri
    var count : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFaktaBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()
        firebaseUser = auth.currentUser!!
        database = FirebaseDatabase.getInstance("https://rpl-cc-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Cek Fakta")

        count = 0

        progresDialog = ProgressDialog(context)
        progresDialog.setTitle("Mohon tunggu sebentar")
        progresDialog.setMessage("Mengirim...")
        progresDialog.setCanceledOnTouchOutside(false)

        binding.btnKirimFakta.setOnClickListener {
            progresDialog.show()
            val id_fakta = database.push().key
            val judul = binding.etJudulFakta.text.toString()
            val nama = binding.etNamaFakta.text.toString()
            val link = binding.etLinkFakta.text.toString()
            val desc = binding.etTeksFakta.text.toString()

            // Validasi form
            if(count != 1){
                Toast.makeText(context,"Masukkan Foto!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(judul.isEmpty()){
                binding.etJudulFakta.error = "Masukkan judul!"
                binding.etJudulFakta.requestFocus()
                return@setOnClickListener
            }else if(nama.isEmpty()) {
                binding.etNamaFakta.error = "Masukkan nama!"
                binding.etNamaFakta.requestFocus()
                return@setOnClickListener
            } else if(link.isEmpty()) {
                binding.etLinkFakta.error = "Masukkan link!"
                binding.etLinkFakta.requestFocus()
                return@setOnClickListener
            } else if(desc.isEmpty()) {
                binding.etTeksFakta.error = "Masukkan Teks!"
                binding.etTeksFakta.requestFocus()
                return@setOnClickListener
            }

            val imageRef = FirebaseStorage.getInstance().reference.child("gambarFakta/${id_fakta}")
            imageRef.putFile(ImageUri!!).addOnSuccessListener{
                val uriTask : Task<Uri> = it.storage.downloadUrl

                while (!uriTask.isSuccessful);
                val uploadedImageUrl = "${uriTask.result}"

                laporFakta(id_fakta, uploadedImageUrl, judul, nama, link, desc)
            }
        }

        // Button Gambar
        binding.ivFotoFakta.setOnClickListener {
            pickImageGalery()
        }

        // Button menuju lapor
        val fragmentLapor = ReportFragment()

        binding.btnLaporBerita.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, fragmentLapor, ReportFragment::class.java.simpleName)
                    .commit()
            }
        }

        return binding.root
    }

    private fun laporFakta(id_fakta : String?,
                           uploadedImageUrl : String,
                           judul : String,
                           nama : String,
                           link : String,
                           desc : String){

        val status = "Sedang Di Proses"
        val id = firebaseUser.uid

        val CekFakta = CekFakta(id, uploadedImageUrl, judul, nama, link, desc, status)


        if(id_fakta != null) {
            database.child(id_fakta).setValue(CekFakta).addOnSuccessListener {
                binding.ivFotoFakta.setImageResource(R.drawable.img_holder)
                binding.etJudulFakta.text.clear()
                binding.etNamaFakta.text.clear()
                binding.etLinkFakta.text.clear()
                binding.etTeksFakta.text.clear()
                count = 0
                progresDialog.dismiss()

                Toast.makeText(context, "Berhasil Disimpan", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                progresDialog.dismiss()
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
                .resize(200,200)
                .into(binding.ivFotoFakta)

        }
    }

}