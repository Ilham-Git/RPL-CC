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
import com.example.hoaxnews.database.Laporan
import com.example.hoaxnews.databinding.FragmentRiwayatLaporanBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class RiwayatLaporanFragment : Fragment(), RiwayatAdapter.OnItemClickListener {

    lateinit var binding: FragmentRiwayatLaporanBinding
    private lateinit var ref: DatabaseReference
    lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var laporanArrayList : ArrayList<Laporan>
    private lateinit var adapter: RiwayatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRiwayatLaporanBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()
        firebaseUser = auth.currentUser!!

        binding.laporanlList.layoutManager = LinearLayoutManager(context)
        binding.laporanlList.setHasFixedSize(true)

        laporanArrayList = arrayListOf<Laporan>()

        adapter = RiwayatAdapter(laporanArrayList, this)

        binding.laporanlList.adapter = adapter

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
        ref = FirebaseDatabase.getInstance("https://rpl-cc-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Laporan")

        val firebaseSearchQuery = ref.orderByChild("title").startAt(search).endAt(search + "\uf8ff")


        firebaseSearchQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                laporanArrayList.clear()
                for(riwayatSnapshot in snapshot.children){
                    if (riwayatSnapshot.child("id_user").getValue()!!.equals(firebaseUser.uid)) {
                        val local = riwayatSnapshot.getValue(Laporan::class.java)
                        laporanArrayList.add(local!!)
                    }
                }
                binding.pbLaporan.setVisibility(View.GONE)
                binding.laporanlList.adapter = RiwayatAdapter(laporanArrayList, this@RiwayatLaporanFragment)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onItemClick(position: Int) {
        val local = laporanArrayList.get(position)
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