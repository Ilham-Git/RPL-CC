package com.example.hoaxnews.user

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.hoaxnews.R
import com.example.hoaxnews.databinding.FragmentLocalDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_local_detail.*


class LocalDetailFragment : Fragment() {

    lateinit var binding: FragmentLocalDetailBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocalDetailBinding.inflate(layoutInflater)

        val args = this.arguments
        val judul = args?.get("nama")
        val isi = args?.get("isi")
        val sumber = args?.get("sumber")
        val gambar = args?.get("gambar")

        binding.tvJudul.text = judul.toString()
        if(gambar !== null) {
            Picasso.get()
                .load(gambar.toString())
                .fit()
                .centerCrop()
                .into(binding.ivBerita)
        }
        binding.tvIsi.text = isi.toString()
        binding.tvSumber.text = sumber.toString()

        return  binding.root
    }
}