package com.example.hoaxnews.user

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hoaxnews.R
import com.example.hoaxnews.database.CekFakta
import com.example.hoaxnews.database.Laporan
import com.example.hoaxnews.databinding.FragmentRiwayatCekBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class RiwayatCekFragment : Fragment(), RiwayatAdapter.OnItemClickListener {

    lateinit var binding: FragmentRiwayatCekBinding
    private lateinit var ref: DatabaseReference
    lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var cekArrayList : ArrayList<Laporan>
    private lateinit var adapter: RiwayatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRiwayatCekBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()
        firebaseUser = auth.currentUser!!

        binding.laporanlList.layoutManager = LinearLayoutManager(context)
        binding.laporanlList.setHasFixedSize(true)

        cekArrayList = arrayListOf<Laporan>()

        adapter = RiwayatAdapter(cekArrayList, this)

        binding.laporanlList.adapter = adapter

        // Button Laporan
        val fragmentRiwayatLaporan = RiwayatLaporanFragment()

        binding.btnLaporan.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, fragmentRiwayatLaporan, RiwayatLaporanFragment::class.java.simpleName)
                    .commit()
            }
        }

        // Search bar
        binding.etLaporan.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val searchText = binding.etLaporan.text.toString()

                getRiwayatData(searchText)

                adapter.notifyDataSetChanged()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        getRiwayatData("")

        return binding.root
    }

    private fun getRiwayatData(search: String){
        binding.pbLaporan.setVisibility(View.VISIBLE)
        ref = FirebaseDatabase.getInstance("https://rpl-cc-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Cek Fakta")

        val firebaseSearchQuery = ref.orderByChild("title").startAt(search).endAt(search + "\uf8ff")


        firebaseSearchQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cekArrayList.clear()
                for(riwayatSnapshot in snapshot.children){
                    if (riwayatSnapshot.child("id_user").getValue()!!.equals(firebaseUser.uid)) {
                        val local = riwayatSnapshot.getValue(Laporan::class.java)
                        cekArrayList.add(local!!)
                    }
                }
                binding.pbLaporan.setVisibility(View.GONE)
                binding.laporanlList.adapter = RiwayatAdapter(cekArrayList, this@RiwayatCekFragment)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onItemClick(position: Int) {
        val local = cekArrayList.get(position)
        val bundle = Bundle()
        bundle.putString("nama", local.title)
        if (!local.image.isNullOrEmpty()) {
            bundle.putString("gambar", local.image)
        }
        bundle.putString("isi", local.desc)
        bundle.putString("sumber", local.link)
        val localDetailFragment = LocalDetailFragment()
        localDetailFragment.arguments = bundle
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.fragment_container, localDetailFragment, LocalDetailFragment::class.java.simpleName)
                .commit()
        }
    }

}