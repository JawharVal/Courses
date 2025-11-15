package com.example.courses

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.courses.domain.model.Course
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottom: BottomNavigationView
    private val courseList = listOf<Course>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.statusBarColor = getColor(R.color.bg_main)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false


        val hostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = hostFragment.navController

        bottom = findViewById(R.id.bottomBar)
        bottom.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottom.isVisible = destination.id != R.id.loginFragment
        }
    }
}
