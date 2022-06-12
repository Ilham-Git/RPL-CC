package com.example.hoaxnews.user


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hoaxnews.R
import com.example.hoaxnews.database.Laporan
import com.example.hoaxnews.model.Users
import com.squareup.picasso.Picasso

class LocalAdapter (private val localList : ArrayList<Laporan>,
                    private val listener: OnItemClickListener
                    ):
    RecyclerView.Adapter<LocalAdapter.LocalViewHolder>() {

    inner class LocalViewHolder (itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val judul : TextView = itemView.findViewById(R.id.tvJudul)
        val gambar : ImageView = itemView.findViewById(R.id.iv_gambarberita)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.local_item, parent, false)
        return LocalViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LocalViewHolder, position: Int) {
        val currentItem = localList[position]

        holder.judul.text = currentItem.title
        holder.nama.text = currentItem.name
        if (!currentItem.image.isNullOrEmpty()) {
            Picasso.get()
                .load(currentItem.image)
                .resize(100, 100)
                .centerCrop()
                .into(holder.gambar)
        }
    }

    override fun getItemCount(): Int {
        return localList.size
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}
