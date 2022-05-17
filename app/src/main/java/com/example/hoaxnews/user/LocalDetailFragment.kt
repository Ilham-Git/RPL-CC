package com.example.hoaxnews.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hoaxnews.R
import com.example.hoaxnews.databinding.FragmentLocalDetailBinding
import com.squareup.picasso.Picasso


class LocalDetailFragment : Fragment() {

    lateinit var binding: FragmentLocalDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocalDetailBinding.inflate(layoutInflater)

        val args = this.arguments
        val judul = args?.get("nama")

        val gambar = args?.get("gambar")

        binding.tvJudul.text = judul.toString()
        if(gambar !== null) {
            Picasso.get()
                .load(gambar.toString())
                .fit()
                .centerCrop()
                .into(binding.ivBerita)
        }

        return  binding.root
    }

}