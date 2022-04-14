package com.example.hoaxnews

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.hoaxnews.databinding.ActivityFormUserBinding
import com.google.firebase.auth.FirebaseAuth

class Form_UserActivity : AppCompatActivity() {
    private lateinit var daftar :  TextView
    lateinit var namas : EditText
    private lateinit var pass : EditText
    lateinit var btnLogin : Button
    lateinit var redirectRegist : TextView

    private lateinit var binding : ActivityFormUserBinding
    private lateinit var actionBar : ActionBar
    private lateinit var progresDialog : ProgressDialog

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_user)

        daftar = findViewById(R.id.daftar)
        namas = findViewById(R.id.username)
        pass = findViewById(R.id.password)
        btnLogin = findViewById(R.id.login)


        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            logins()
        }

        daftar.setOnClickListener {
            val intent = Intent(this, Form_RegisterActivity::class.java)
            startActivity(intent)
        }

    }


    private fun logins() {
        val nama = namas.text.toString().trim()
        val pass = pass.text.toString()

        auth.signInWithEmailAndPassword(nama, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Berhasil Login", Toast.LENGTH_SHORT).show()
            }  else {
                Toast.makeText(this, "Gagal Login", Toast.LENGTH_SHORT).show()
            }
        }




    }
}