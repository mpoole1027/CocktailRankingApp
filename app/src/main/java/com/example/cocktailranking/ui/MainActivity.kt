package com.example.cocktailranking.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.example.cocktailranking.R
import androidx.appcompat.app.ActionBarDrawerToggle

// Main activity that hosts the navigation drawer and fragments
class MainActivity : AppCompatActivity()
{

    // Navigation and layout components
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    // Called when activity is created
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up the toolbar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Initialize drawer and navigation view
        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)

        // Get NavController from NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Define top-level destinations for navigation drawer
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.rankingFragment,
                R.id.searchFragment
            ),
            drawerLayout
        )

        // Connect NavController to toolbar and drawer navigation
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(navigationView, navController)

        // Lock drawer when not on top-level destinations
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isTopLevel = destination.id == R.id.homeFragment ||
                    destination.id == R.id.rankingFragment ||
                    destination.id == R.id.searchFragment

            drawerLayout.setDrawerLockMode(
                if (isTopLevel) DrawerLayout.LOCK_MODE_UNLOCKED
                else DrawerLayout.LOCK_MODE_LOCKED_CLOSED
            )
        }
    }

    // Enable up button support
    override fun onSupportNavigateUp(): Boolean
    {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}
