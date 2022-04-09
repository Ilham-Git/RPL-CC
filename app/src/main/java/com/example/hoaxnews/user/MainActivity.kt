package com.example.hoaxnews.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.hoaxnews.R

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hitung = 0
        count = 0

        val btnLapor = findViewById<Button>(R.id.btn_lapor)
        btnLapor.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btn_lapor ->{
                val intent = Intent(this, LaporanActivity::class.java)
                startActivity(intent)
            }
        }
    }
}