package com.example.hoaxnews.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.hoaxnews.R
import com.example.hoaxnews.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnLapor = findViewById<Button>(R.id.btn_lapor)
        btnLapor.setOnClickListener(this)

        binding.btnProfile.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btn_lapor ->{
                val intent = Intent(this, UserActivity::class.java)
                startActivity(intent)
            }
        }
    }
}