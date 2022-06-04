package com.example.hoaxnews.user

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hoaxnews.EdukasiActivity
import com.example.hoaxnews.R
import com.example.hoaxnews.database.Berita
import com.example.hoaxnews.database.Laporan
import com.example.hoaxnews.databinding.FragmentBerandaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class BerandaFragment : Fragment(), BeritaAdapter.OnItemClickListener {

    lateinit var binding: FragmentBerandaBinding
    private lateinit var ref: DatabaseReference
    private lateinit var beritaArrayList : ArrayList<Berita>
    private lateinit var adapter: BeritaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBerandaBinding.inflate(layoutInflater)

        binding.beritaList.layoutManager = LinearLayoutManager(context)
        binding.beritaList.setHasFixedSize(true)

        beritaArrayList = arrayListOf<Berita>()

        adapter = BeritaAdapter(beritaArrayList, this)

        binding.beritaList.adapter = adapter

        // Button Edukasi
        binding.btnEdukasi.setOnClickListener{
            val intent = Intent(context, EdukasiActivity::class.java)
            startActivity(intent)
        }


        // Search bar
        binding.etBerita.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val searchText = binding.etBerita.text.toString()

                getBeritaData(searchText)

                adapter.notifyDataSetChanged()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        getBeritaData("")

        return binding.root
    }

    private fun getBeritaData(search: String){
        binding.pbBerita.setVisibility(View.VISIBLE)
        ref = FirebaseDatabase.getInstance("https://rpl-cc-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("berita")

        val firebaseSearchQuery = ref.orderByChild("judul").startAt(search).endAt(search + "\uf8ff")


        firebaseSearchQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                beritaArrayList.clear()
                for(beritaSnapshot in snapshot.children){
                    val berita = beritaSnapshot.getValue(Berita::class.java)
                    beritaArrayList.add(berita!!)
                }
                binding.pbBerita.setVisibility(View.GONE)
                binding.beritaList.adapter = BeritaAdapter(beritaArrayList, this@BerandaFragment)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onItemClick(position: Int) {
        val berita = beritaArrayList.get(position)
        val bundle = Bundle()
        bundle.putString("nama", berita.judul)
        if (!berita.gambar.isNullOrEmpty()) {
            bundle.putString("gambar", berita.gambar)
        }
        bundle.putString("sumber", berita.link)
        val localDetailFragment = LocalDetailFragment()
        localDetailFragment.arguments = bundle
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.fragment_container, localDetailFragment, LocalDetailFragment::class.java.simpleName)
                .commit()
        }
    }

}