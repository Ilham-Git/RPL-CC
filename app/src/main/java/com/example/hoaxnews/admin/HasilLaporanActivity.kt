package com.example.hoaxnews.admin

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hoaxnews.R
import com.example.hoaxnews.user.DetailLaporanActivity
import com.example.hoaxnews.user.FragmentLaporan

class HasilLaporanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hasil_laporan)

        // getting the recyclerview by its id
        val recyclerView = findViewById<RecyclerView>(R.id.rvLapor)

        // this creates a vertical layout Manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // menangkap data uri dari gambar
        val picture = getIntent().getStringExtra("uri")
        val uri = Uri.parse(picture)

        val bundle = intent.extras
        if (bundle != null){
            // This loop will create 20 Views containing
            // the image with the count of view
            for (i in 1..20) {
                data.add(ItemsViewModel(uri,"${bundle.getString("judul")}","${bundle.getString("nama")}",
                    "${bundle.getString("link")}","${bundle.getString("desc")}"))
            }
        }

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : CustomAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(this@HasilLaporanActivity, "Anda mengklik item nomer ${position+1}", Toast.LENGTH_SHORT).show()

                val bundle = Bundle()
                bundle.putString("gambar", data[position].image.toString())
                bundle.putString("judul", data[position].title)
                bundle.putString("desc", data[position].desc)

                val intent = Intent(this@HasilLaporanActivity, DetailLaporanActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        })
    }
}