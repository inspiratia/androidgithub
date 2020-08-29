package com.norman1.normanbhaskara.ui.activities

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.preference.PreferenceManager
import com.norman1.normanbhaskara.R
import com.norman1.normanbhaskara.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        navController = findNavController(R.id.nav_host)
        NavigationUI.setupWithNavController(
            binding.bottomNavigationView,
            navController
        )
        setTheme()

        appBarConfiguration = AppBarConfiguration(navController.graph)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.details_destination -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                R.id.settings_destination -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                else -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp() =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    private fun setTheme() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        when (sharedPreferences.getString(resources.getString(R.string.theme_key), "")) {
            resources.getStringArray(R.array.themeEntryArray)[0] -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            resources.getStringArray(R.array.themeEntryArray)[1] -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                }
            }
        }
    }
}
