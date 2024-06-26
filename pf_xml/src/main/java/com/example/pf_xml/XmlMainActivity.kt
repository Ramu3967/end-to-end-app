package com.example.pf_xml

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.pf_xml.databinding.ActivityXmlMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class XmlMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityXmlMainBinding

    private val navController: NavController by lazy {
        val hostFrag = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        hostFrag.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityXmlMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
        setupBottomNav()
    }


    private fun setupActionBar(){
        setSupportActionBar(binding.toolbar)
    }

    private fun setupBottomNav(){
        binding.bottomNavigation.setupWithNavController(navController)
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.search -> {navController.navigate(R.id.searchFragment); true}
                R.id.animalsNearYou -> {navController.navigate(R.id.animalsNearYou); true}
                else -> false
            }
        }
    }
}