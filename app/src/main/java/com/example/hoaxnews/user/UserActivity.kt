package com.example.hoaxnews.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.hoaxnews.R
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {

    private val fragmentProfile = FragmentProfile()
    private val fragmentLokal = LocalFragment()
    private val fragmentReport = ReportFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        replaceFragment(fragmentLokal)

        bottom_navigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_profil -> replaceFragment(fragmentProfile)
                R.id.menu_lapor -> replaceFragment(fragmentReport)
                R.id.menu_beranda -> replaceFragment(fragmentLokal)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}