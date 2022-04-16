package com.example.hoaxnews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hoaxnews.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

            binding.user.setOnClickListener {
                startActivity(Intent(this, Form_UserActivity::class.java))
            }

            binding.admin.setOnClickListener {
                startActivity(Intent(this, Form_AdminActivity::class.java))
            }

    }
}