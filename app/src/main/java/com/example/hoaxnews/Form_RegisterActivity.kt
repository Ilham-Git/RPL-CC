package com.example.hoaxnews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Form_RegisterActivity : AppCompatActivity() {

    lateinit var namas : EditText;
    lateinit var pass : EditText;
    lateinit var RedirectLogin : TextView;
    private lateinit var regist : Button;

    // firebase autentikasi
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_register)

        // Find id xml
        namas = findViewById(R.id.username)
        pass = findViewById(R.id.password)
        regist = findViewById(R.id.login)
        RedirectLogin = findViewById(R.id.redirect)

        auth = Firebase.auth

        regist.setOnClickListener {
            signUpUser()
        }

        RedirectLogin.setOnClickListener {
            val intent = Intent(this, Form_UserActivity::class.java)
            startActivity(intent)
        }

    }

    private fun signUpUser() {
        val nama = namas.text.toString()
        val pass = pass.text.toString()

        // check pass
        if (nama.isBlank() || pass.isBlank()) {
            Toast.makeText(this, "Nama dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }

        // jika nama dan pass benar
        auth.createUserWithEmailAndPassword(nama, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Berhasil Register", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Gagal Register", Toast.LENGTH_SHORT).show()
            }
        }
    }
}