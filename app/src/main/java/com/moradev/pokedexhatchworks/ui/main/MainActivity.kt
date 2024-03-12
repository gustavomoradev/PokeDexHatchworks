package com.moradev.pokedexhatchworks.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.moradev.pokedexhatchworks.R
import com.moradev.pokedexhatchworks.common.DateUtility
import com.moradev.pokedexhatchworks.common.Stage
import com.moradev.pokedexhatchworks.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController:NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setUpAppBar()


        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    private fun setUpAppBar() {
        binding.appBar.backgroundTintList =
            when(DateUtility.getState()){
                Stage.MORNING -> ContextCompat.getColorStateList(this@MainActivity, R.color.light_blue)
                Stage.AFTERNOON -> ContextCompat.getColorStateList(this@MainActivity, R.color.red)
                Stage.NIGHT -> ContextCompat.getColorStateList(this@MainActivity, R.color.black2)
            }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater :MenuInflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
//        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){

            android.R.id.home -> {
                navController.popBackStack()
                true
            }

            R.id.exchange_rate -> {
                val dialog = BottomSheetDialogExchange()
                dialog.show(this@MainActivity.supportFragmentManager, dialog.tag)
                true
            }
            else -> true
        }
//        return super.onOptionsItemSelected(item)
    }
}