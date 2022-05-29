package com.example.hoaxnews.user

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hoaxnews.R
import com.example.hoaxnews.databinding.FragmentEditProfileBinding
import com.example.hoaxnews.model.Users
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_edit_profile.*


class FragmentEditProfile : Fragment() {

    lateinit var binding : FragmentEditProfileBinding
    lateinit var auth: FirebaseAuth
    lateinit var ImageUri: Uri
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var progressDialog: ProgressDialog
    var imageEdited : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEditProfileBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        firebaseUser = auth.currentUser!!

        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Mohon tunggu sebentar")
        progressDialog.setMessage("Mengupdate Profile")
        progressDialog.setCanceledOnTouchOutside(false)

        val fragmentProfile = FragmentProfile()

        // Button Kembali
        binding.btnKembali.setOnClickListener{
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, fragmentProfile, FragmentProfile::class.java.simpleName)
                    .addToBackStack(null)
                    .commit()
            }
        }

        // Gambar Profile
        binding.ivFotoProfile.setOnClickListener{
            val email = binding.etEmail.text.toString().trim()

            if (email.isEmpty()){
                binding.etEmail.error = "Email kosong!"
                binding.etEmail.requestFocus()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.etEmail.error = "Email tidak valid!"
                binding.etEmail.requestFocus()
            }

            imageGallery()
        }

        // Button Update
        binding.btnUbah.setOnClickListener{
            if(imageEdited){
                uploadImage()
            }else{
                updateProfilewithoutImage()
            }
        }


        userInfo()

        return binding.root
    }

    // Mengupdate data User dengan gambar
    private fun updateProfilewithImage(uploadedImageUrl: String) {
        val nama = binding.etNamaProfile.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val no_hp = binding.etNomorHP.text.toString().trim()
        val lokasi = binding.etLokasi.text.toString().trim()

        val userMap = HashMap<String, Any>()
        userMap["id"] = firebaseUser.uid
        userMap["nama"] = nama
        userMap["email"] = email
        userMap["no_hp"] = no_hp
        userMap["lokasi"] = lokasi
        userMap["gambar"] = uploadedImageUrl

        progressDialog.show()

        val ref = FirebaseDatabase.getInstance("https://rpl-cc-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users").child(firebaseUser.uid)
        firebaseUser.updateEmail(email).addOnSuccessListener {
            ref.updateChildren(userMap).addOnCompleteListener{
                if(it.isSuccessful){
                    val fragmentProfile = FragmentProfile()
                    fragmentManager?.beginTransaction()?.apply {
                        replace(R.id.fragment_container, fragmentProfile, FragmentProfile::class.java.simpleName)
                            .commit()
                    }
                    progressDialog.dismiss()
                    Toast.makeText(context, "Profil telah diupdate", Toast.LENGTH_SHORT).show()
                }
                else{
                    val fragmentProfile = FragmentProfile()
                    fragmentManager?.beginTransaction()?.apply {
                        replace(R.id.fragment_container, fragmentProfile, FragmentProfile::class.java.simpleName)
                            .commit()
                    }
                    progressDialog.dismiss()
                    Toast.makeText(context, "Profil gagal diupdate", Toast.LENGTH_SHORT).show()
                }
            }
        }.addOnFailureListener{
            val fragmentProfile = FragmentProfile()
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, fragmentProfile, FragmentProfile::class.java.simpleName)
                    .commit()
            }
            progressDialog.dismiss()
            Toast.makeText(context, "Profil gagal diupdate", Toast.LENGTH_SHORT).show()
        }
    }

    // Mengupdate data user tanpa gambar
    private fun updateProfilewithoutImage() {
        val nama = binding.etNamaProfile.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val no_hp = binding.etNomorHP.text.toString().trim()
        val lokasi = binding.etLokasi.text.toString().trim()

        if (email.isEmpty()){
            binding.etEmail.error = "Email kosong!"
            binding.etEmail.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmail.error = "Email tidak valid!"
            binding.etEmail.requestFocus()
        }

        progressDialog.show()

        val userMap = HashMap<String, Any>()
        userMap["id"] = firebaseUser.uid
        userMap["nama"] = nama
        userMap["email"] = email
        userMap["no_hp"] = no_hp
        userMap["lokasi"] = lokasi

        val ref = FirebaseDatabase.getInstance("https://rpl-cc-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users").child(firebaseUser.uid)

        // Ganti Email Di Authentication
        firebaseUser.updateEmail(email).addOnSuccessListener {

            // Ganti data di Realtime Database
            ref.updateChildren(userMap).addOnCompleteListener {
                if (it.isSuccessful) {
                    val fragmentProfile = FragmentProfile()
                    fragmentManager?.beginTransaction()?.apply {
                        replace(
                            R.id.fragment_container,
                            fragmentProfile,
                            FragmentProfile::class.java.simpleName
                        )
                            .commit()
                    }
                    progressDialog.dismiss()
                    Toast.makeText(context, "Profil telah diupdate", Toast.LENGTH_SHORT).show()

                } else {
                    val fragmentProfile = FragmentProfile()
                    fragmentManager?.beginTransaction()?.apply {
                        replace(
                            R.id.fragment_container,
                            fragmentProfile,
                            FragmentProfile::class.java.simpleName
                        )
                            .commit()
                    }
                    progressDialog.dismiss()
                    Toast.makeText(context, "Profil gagal diupdate", Toast.LENGTH_SHORT).show()

                }
            }
        }.addOnFailureListener {

            val fragmentProfile = FragmentProfile()
            fragmentManager?.beginTransaction()?.apply {
                replace(
                    R.id.fragment_container,
                    fragmentProfile,
                    FragmentProfile::class.java.simpleName
                )
                    .commit()
            }
            progressDialog.dismiss()
            Toast.makeText(context, "Profil gagal diupdate", Toast.LENGTH_SHORT).show()
        }
    }

    // Ambil gambar dari Gallery
    private fun imageGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == RESULT_OK){
            ImageUri = data?.data!!

            Picasso.get()
                .load(ImageUri)
                .resize(200,200)
                .into(binding.ivFotoProfile)

            imageEdited = true
        }
    }

    // Simpan gambar
    private fun uploadImage(){
        val userRef = FirebaseStorage.getInstance().reference.child("gambarProfile/${firebaseUser.uid}")
        userRef.putFile(ImageUri!!).addOnSuccessListener {
            val uriTask: Task<Uri> = it.storage.downloadUrl
            while (!uriTask.isSuccessful);
            val uploadedImageUrl = "${uriTask.result}"

            updateProfilewithImage(uploadedImageUrl)
        }
    }

    // Info User
    private fun userInfo() {
        val userRef = FirebaseDatabase.getInstance("https://rpl-cc-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users").child(firebaseUser.uid)
        userRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val user =snapshot.getValue<Users>(Users::class.java)
                    if(!user!!.gambar.isNullOrEmpty()) {
                        Picasso.get()
                            .load(user!!.gambar)
                            .resize(200, 200)
                            .into(binding.ivFotoProfile)
                    }
                    binding.etNamaProfile.setText(user!!.nama)
                    binding.etEmail.setText(firebaseUser.email)
                    binding.etNomorHP.setText(user!!.no_hp)
                    binding.etLokasi.setText(user!!.lokasi)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show()
            }

        })
    }
}