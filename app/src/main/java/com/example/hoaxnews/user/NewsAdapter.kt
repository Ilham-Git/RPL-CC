package com.example.hoaxnews.user

import android.location.GnssAntennaInfo
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hoaxnews.R
import com.example.hoaxnews.admin.CustomAdapter
import com.example.hoaxnews.admin.ItemsViewModel
import com.example.hoaxnews.database.Laporan
import com.example.hoaxnews.model.Users
import com.squareup.picasso.Picasso

class NewsAdapter(private val NewsList:ArrayList<ItemsViewModel>, private val Listener: FragmentLaporan) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent:ViewGroup,viewType:Int):ViewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_view_design, parent, false)
        return ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder:ViewHolder,position:Int){
        val currentItem = NewsList[position]
//        val imageUri = Uri.parse(currentItem.image.toString())
//        holder.gambar.setImageURI(imageUri)
        if (!currentItem.image.isNullOrEmpty()) {
            Picasso.get()
                .load(currentItem.image)
                .resize(100, 100)
                .centerCrop()
                .into(holder.gambar)
        }
        holder.judul.text = currentItem.title
        holder.nama.text = currentItem.name
        holder.link.text = currentItem.link
        holder.status.text = currentItem.status
    }
    override fun getItemCount():Int{
        return NewsList.size
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val gambar: ImageView = itemView.findViewById(R.id.cvGambar)
        val judul: TextView = itemView.findViewById(R.id.cvJudul)
        val nama: TextView = itemView.findViewById(R.id.cvNama)
        val link: TextView = itemView.findViewById(R.id.cvLink)
        val status: TextView = itemView.findViewById(R.id.cvStatus)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                Listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}