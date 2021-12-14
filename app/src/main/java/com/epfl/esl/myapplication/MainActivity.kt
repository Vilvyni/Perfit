package com.epfl.esl.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.epfl.esl.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
//    "Heeey I made the login work"

    private lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

       val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
       drawerLayout = binding.drawerLayout

        //val navController = this.findNavController(R.id.mainFragment)
        //NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        //NavigationUI.setupWithNavController(binding.navView, navController)

    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.mainFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}