package com.example.hoaxnews.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hoaxnews.R

class CustomAdapter(private val mList: List<ItemsViewModel>): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int){

        }
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    class ViewHolder(ItemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(ItemView) {
        val gambar: ImageView = itemView.findViewById(R.id.gambar)
        val judul: TextView = itemView.findViewById(R.id.judul)
        val nama: TextView = itemView.findViewById(R.id.nama)
        val link: TextView = itemView.findViewById(R.id.link)
//        val desc: TextView = itemView.findViewById(R.id.desc)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_design, parent, false)
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        // sets the image to the imageview from our itemHolder class
        holder.gambar.setImageURI(ItemsViewModel.image)
        // sets the text to the textview from our itemHolder class
        holder.judul.text = ItemsViewModel.title
        holder.nama.text = ItemsViewModel.name
        holder.link.text = ItemsViewModel.link
//        holder.desc.text = ItemsViewModel.desc
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}