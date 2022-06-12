package com.example.hoaxnews.user

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hoaxnews.R
import com.example.hoaxnews.database.Laporan
import com.squareup.picasso.Picasso

class RiwayatAdapter(private val riwayatList : ArrayList<Laporan>,
                     private val listener: OnItemClickListener
                     ):
    RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder>() {

    inner class RiwayatViewHolder (itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val judul : TextView = itemView.findViewById(R.id.tvJudul)
        val gambar : ImageView = itemView.findViewById(R.id.iv_gambarberita)
        val status : TextView = itemView.findViewById(R.id.tv_status)

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): RiwayatAdapter.RiwayatViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.riwayat_item, parent, false)
        return RiwayatViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RiwayatAdapter.RiwayatViewHolder, position: Int) {
        val currentItem = riwayatList[position]

        holder.judul.text = currentItem.title
        if (!currentItem.image.isNullOrEmpty()) {
            Picasso.get()
                .load(currentItem.image)
                .resize(100, 100)
                .centerCrop()
                .into(holder.gambar)
        }
        if(currentItem.status == "Sedang Di Proses"){
            holder.status.setTextColor(Color.BLUE)
        }else if(currentItem.status == "Hoax"){
            holder.status.setTextColor(Color.RED)
        }else if(currentItem.status == "Fakta"){
            holder.status.setTextColor(Color.parseColor("#04764E"))
        }
        holder.status.text = currentItem.status
    }

    override fun getItemCount(): Int {
        return riwayatList.size
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}