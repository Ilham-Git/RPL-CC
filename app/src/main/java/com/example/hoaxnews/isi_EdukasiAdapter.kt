package com.example.hoaxnews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class isi_EdukasiAdapter(val menu: List<isi_Edukasi>) :
    RecyclerView.Adapter<isi_EdukasiAdapter.EdukasiVH>()
{
    class EdukasiVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var titleTxt : TextView = itemView.findViewById(R.id.title_card)
        var edukasiTxt : TextView = itemView.findViewById(R.id.desc_card)
        var linear : LinearLayout = itemView.findViewById(R.id.linear)
        var expand_layout : RelativeLayout = itemView.findViewById(R.id.expand_layout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EdukasiVH {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.single_edukasi, parent, false)

        return EdukasiVH(view)
    }

    override fun onBindViewHolder(holder: EdukasiVH, position: Int) {

        val isi_menu : isi_Edukasi = menu[position]
        holder.titleTxt.text = isi_menu.title
        holder.edukasiTxt.text = isi_menu.deskripsi

        val terExpand : Boolean = menu[position].expand
        holder.expand_layout.visibility = if(terExpand) View.VISIBLE else View.GONE

        holder.linear.setOnClickListener {
            val edu = menu[position]
            edu.expand = !edu.expand
            notifyItemChanged(position)
        }

    }

    override fun getItemCount(): Int {
        return menu.size
    }


}