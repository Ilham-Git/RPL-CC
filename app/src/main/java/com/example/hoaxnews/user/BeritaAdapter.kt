package com.example.hoaxnews.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hoaxnews.R
import com.example.hoaxnews.database.Berita
import com.squareup.picasso.Picasso

class BeritaAdapter (private val beritaList : ArrayList<Berita>,
                     private val listener: OnItemClickListener
                     ):
    RecyclerView.Adapter<BeritaAdapter.BeritaViewHolder>(){
        inner class BeritaViewHolder (itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
            val judul : TextView = itemView.findViewById(R.id.tvJudul)
            val gambar : ImageView = itemView.findViewById(R.id.iv_gambarberita)
            val tanggal : TextView = itemView.findViewById(R.id.tv_waktu)
            val nama: TextView = itemView.findViewById(R.id.tv_sumber)

            init {
                itemView.setOnClickListener(this)
            }


            override fun onClick(p0: View?) {
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeritaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.local_item, parent, false)
        return BeritaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BeritaViewHolder, position: Int) {
        val currentItem = beritaList[position]

        holder.judul.text = currentItem.judul
        holder.nama.text = "admin"
        holder.tanggal.text = currentItem.tanggal_buat
        if (!currentItem.image.isNullOrEmpty()) {
            Picasso.get()
                .load(currentItem.image)
                .resize(100, 100)
                .centerCrop()
                .into(holder.gambar)
        }
    }

    override fun getItemCount(): Int {
        return beritaList.size
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}