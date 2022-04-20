package com.example.hoaxnews.user

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_login.*


class FragmentEditProfile : Fragment() {

    lateinit var binding : FragmentEditProfileBinding
    private val fragmentProfile = FragmentProfile()
    lateinit var auth: FirebaseAuth
    lateinit var ImageUri: Uri
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEditProfileBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        firebaseUser = auth.currentUser!!

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
            imageGallery()
        }

        // Button Update
        updateProfile()

//        userInfo()

        return binding.root
    }

    // Mengupdate data User
    private fun updateProfile() {
        val userRef = FirebaseStorage.getInstance().reference.child("gambarProfile/${firebaseUser.uid}")
        userRef.putFile(ImageUri!!).addOnSuccessListener {
            val uriTask: Task<Uri> = it.storage.downloadUrl
            while (!uriTask.isSuccessful);
            val uploadedImageUrl ="${uriTask.result}"

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

            val ref = FirebaseDatabase.getInstance().getReference("USERS").child(firebaseUser.uid)
            ref.updateChildren(userMap).addOnCompleteListener{
                if(it.isSuccessful){
                    fragmentManager?.beginTransaction()?.apply {
                        replace(R.id.fragment_container, fragmentProfile, FragmentProfile::class.java.simpleName)
                            .commit()
                    }
                    Toast.makeText(context, "Profil telah diupdate", Toast.LENGTH_SHORT).show()
                }
                else{
                    fragmentManager?.beginTransaction()?.apply {
                        replace(R.id.fragment_container, fragmentProfile, FragmentProfile::class.java.simpleName)
                            .commit()
                    }
                    Toast.makeText(context, "Profil gagal diupdate", Toast.LENGTH_SHORT).show()
                }
            }
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

            //Picasso
        }
    }

    // Info User
    private fun userInfo() {
        val userRef = FirebaseDatabase.getInstance().getReference("USERS").child(firebaseUser.uid)
        userRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val user =snapshot.getValue<Users>(Users::class.java)
                    if (user!!.gambar != ""){
                        //Picasso
                    }
                    binding.etNamaProfile.setText(user!!.nama)
                    binding.etEmail.setText(firebaseUser.email)
                    binding.etNomorHP.setText(user!!.no_hp)
                    binding.etLokasi.setText(user!!.lokasi)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}