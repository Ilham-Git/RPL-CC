package com.example.hoaxnews

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.hoaxnews.databinding.ActivityFormUserBinding
import com.example.hoaxnews.user.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Form_UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormUserBinding
//    private lateinit var actionBar: ActionBar
    private lateinit var progresDialog: ProgressDialog

    lateinit var auth: FirebaseAuth
    private var namas = ""
    private var pass = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
//
//        actionBar = supportActionBar!!
//        actionBar.title = "Login"

        progresDialog = ProgressDialog(this)
        progresDialog.setTitle("Mohon tunggu sebentar")
        progresDialog.setMessage("Masuk....")
        progresDialog.setCanceledOnTouchOutside(false)


        auth = FirebaseAuth.getInstance()
//        checkuser()

        binding.daftar.setOnClickListener {
            startActivity(Intent(this, Form_RegisterActivity::class.java))
            finish()
        }

        binding.login.setOnClickListener {
            validasiData()
        }

        binding.back.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
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
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        progresDialog.show()
        auth.signInWithEmailAndPassword(namas, pass)
            .addOnSuccessListener {
                progresDialog.dismiss()
                    val fbUser = auth.currentUser
                    val nama = fbUser!!.displayName
                    Toast.makeText(this, "Berhasil login sebagai ${nama}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
            }
            .addOnFailureListener { e->
                progresDialog.dismiss()
                Toast.makeText(this, "Gagal Login ke ${e.message} ", Toast.LENGTH_SHORT).show()
            }
    }

//    private fun checkuser() {
//        val firebaseUser = auth.currentUser
//        if (firebaseUser != null) {
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//        }
//    }

}