package com.ihsan.news_app.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

import com.ihsan.news_app.R
import com.ihsan.news_app.databinding.ActivityMainBinding
import com.ihsan.news_app.ui.fragment.BookmarksFragment
import com.ihsan.news_app.ui.fragment.HomeFragment
import com.ihsan.news_app.ui.fragment.TabLayoutFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
//
//        navController = navHostFragment.findNavController()
//        setupActionBarWithNavController(navController)
//        replaceFragment(HomeFragment())
        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home-> {
                    replaceFragment(TabLayoutFragment())
                    Toast.makeText(this, "Read More CLicked", Toast.LENGTH_SHORT).show()
                }
                else-> {
                    replaceFragment(BookmarksFragment())
                    Toast.makeText(this, "Read More CLicked", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,fragment)
        fragmentTransaction.commit()
    }
}