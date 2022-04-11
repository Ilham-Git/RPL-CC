package com.example.hoaxnews.user

import android.content.Intent
import android.content.Intent.getIntent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import com.example.hoaxnews.R
import com.example.hoaxnews.databinding.FragmentLaporanBinding
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_laporan.*

class FragmentLaporan : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_laporan, container, false)
        val bundle = arguments
        val image = bundle!!.getString("gambar")
        val uri = Uri.parse(image)
        val title = bundle.getString("judul")
        val desc = bundle.getString("desc")

        ivFragGambarLapor.setImageURI(uri)
        tvFragJudulLapor.text = title
        tvFragDescLapor.text = desc

        return super.onCreateView(inflater, container, savedInstanceState)
    }
}