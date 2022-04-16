package com.example.hoaxnews

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.hoaxnews.databinding.ActivityFormRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Form_RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFormRegisterBinding
    private lateinit var actionBar: ActionBar
    private lateinit var progresDialog : ProgressDialog
    private var namas = ""
    private var pass = ""
    lateinit var redirectLogin : TextView

    // firebase autentikasi
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        redirectLogin = findViewById(R.id.redirect)

        actionBar = supportActionBar!!
        actionBar.title = "Register"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        progresDialog = ProgressDialog(this)
        progresDialog.setTitle("Mohon tunggu sebentar")
        progresDialog.setMessage("Membuat akun....")
        progresDialog.setCanceledOnTouchOutside(false)

        auth = FirebaseAuth.getInstance()

        binding.login.setOnClickListener {
            validasiData()
        }

        redirectLogin.setOnClickListener {
            startActivity(Intent(this, Form_UserActivity::class.java))
        }

    }

    private fun validasiData() {
        namas = binding.username.text.toString().trim()
        pass = binding.password.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(namas).matches()) {
            binding.username.setError("Harus format Email")
        }
        else if (TextUtils.isEmpty(pass)) {
            binding.password.error = "Mohon masukkan password anda"
        }
        else {
            firebaseRegister()
        }
    }

    private fun firebaseRegister() {
        progresDialog.show()

        auth.createUserWithEmailAndPassword(namas, pass)
            .addOnSuccessListener {
                val fbuser = auth.currentUser
                val nama = fbuser!!.displayName
                Toast.makeText(this, "Akun berhasil dibuat dengan nama ${nama}", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this,  Form_UserActivity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                progresDialog.dismiss()
                Toast.makeText(this, "Gagal Login ke ${e.message} ", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()

        return super.onSupportNavigateUp()
    }

}