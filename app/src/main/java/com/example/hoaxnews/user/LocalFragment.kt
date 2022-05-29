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
import com.example.hoaxnews.databinding.FragmentLocalBinding
import com.google.firebase.database.*


class LocalFragment : Fragment(), LocalAdapter.OnItemClickListener {

    lateinit var binding : FragmentLocalBinding
    private lateinit var ref: DatabaseReference
    private lateinit var localArrayList : ArrayList<Laporan>
    private lateinit var adapter: LocalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLocalBinding.inflate(layoutInflater)

        binding.localList.layoutManager = LinearLayoutManager(context)
        binding.localList.setHasFixedSize(true)

        localArrayList = arrayListOf<Laporan>()

        adapter = LocalAdapter(localArrayList, this)

        binding.localList.adapter = adapter


        // Search bar
        binding.etBerita.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val searchText = binding.etBerita.text.toString()

                getLocalData(searchText)

                adapter.notifyDataSetChanged()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        getLocalData("")

        return binding.root
    }


    private fun getLocalData(search: String){
        binding.pbBerita.setVisibility(View.VISIBLE)
        ref = FirebaseDatabase.getInstance("https://rpl-cc-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Laporan")

        val firebaseSearchQuery = ref.orderByChild("title").startAt(search).endAt(search + "\uf8ff")


        firebaseSearchQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                localArrayList.clear()
                for(localSnapshot in snapshot.children){
                    if (localSnapshot.child("status").getValue()!!.equals("Hoax")) {
                        val local = localSnapshot.getValue(Laporan::class.java)
                        localArrayList.add(local!!)
                    }
                }
                binding.pbBerita.setVisibility(View.GONE)
                binding.localList.adapter = LocalAdapter(localArrayList, this@LocalFragment)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onItemClick(position: Int) {
        val local = localArrayList.get(position)
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