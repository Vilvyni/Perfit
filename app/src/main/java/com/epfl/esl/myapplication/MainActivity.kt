package com.epfl.esl.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.epfl.esl.myapplication.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout

        val navController = this.findNavController(R.id.mainFragment)
        val bottomNavigationView = binding.bottomMenuView
        bottomNavigationView.setupWithNavController(navController)


//        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
//        NavigationUI.setupWithNavController(binding.navView, navController)


//        setBottomNavigationVisibility(View.GONE)




    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.mainFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    public fun setBottomNavigationVisibility (visibility: Int){
        bottomNavigationView.visibility = visibility
    }
}
