package com.example.hoaxnews.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hoaxnews.R
import com.example.hoaxnews.databinding.FragmentProfileBinding
import com.example.hoaxnews.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_profile.*


class FragmentProfile : Fragment() {

    lateinit var binding : FragmentProfileBinding
    lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        firebaseUser = auth.currentUser!!

        val fragmentEditProfile = FragmentEditProfile()

        // Button Edit
        binding.btnEdit.setOnClickListener{
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, fragmentEditProfile, FragmentEditProfile::class.java.simpleName)
                    .addToBackStack(null)
                    .commit()
            }
        }

        userInfo()

        return binding.root
    }

    // Info User
    private fun userInfo() {
        val userRef = FirebaseDatabase.getInstance("https://rpl-cc-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users").child(firebaseUser.uid)
        userRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val user =snapshot.getValue<Users>(Users::class.java)
                    if (!user!!.gambar.isNullOrEmpty()) {
                        Picasso.get()
                            .load(user!!.gambar)
                            .resize(200, 200)
                            .into(binding.ivFotoProfile)
                    }
                    binding.tvNamaProfile.text = user!!.nama
                    binding.tvEmail.text = firebaseUser.email
                    binding.tvNomorHP.text = user!!.no_hp
                    binding.tvLokasi.text = user!!.lokasi
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
            }

        })
    }
}