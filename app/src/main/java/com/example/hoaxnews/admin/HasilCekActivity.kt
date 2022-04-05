package com.example.hoaxnews.admin

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hoaxnews.R

class HasilCekActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hasil_cek)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerView)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // Menangkap data uri dari gambar
        val picture = getIntent().getStringExtra("uri")
        val uri = Uri.parse(picture)

        val bundle = intent.extras
        if (bundle != null){
            // This loop will create 20 Views containing
            // the image with the count of view
            for (i in 1..20) {
                data.add(ItemsViewModel(uri, "${bundle.getString("judul")}", "${bundle.getString("nama")}",
                    "${bundle.getString("link")}", ""))
            }
        }

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }
}