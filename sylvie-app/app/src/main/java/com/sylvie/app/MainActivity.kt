package com.sylvie.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sylvie.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Debug: verificar que el fragment existe
        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        Log.d("MainActivity", "Fragment encontrado: ${fragment != null}")

        try {
            val navView: BottomNavigationView = binding.bottomNav
            val navController = findNavController(R.id.nav_host_fragment)

            val appBarConfiguration = AppBarConfiguration(
                setOf(R.id.nav_scan, R.id.nav_history, R.id.nav_profile)
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        } catch (e: Exception) {
            Log.e("MainActivity", "Error: ${e.message}")
            e.printStackTrace()
        }
    }
}