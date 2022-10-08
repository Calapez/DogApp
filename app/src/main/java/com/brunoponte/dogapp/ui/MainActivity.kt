package com.brunoponte.dogapp.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.brunoponte.dogapp.R
import com.brunoponte.dogapp.databinding.ActivityMainBinding
import com.brunoponte.dogapp.ui.breedList.BreedListFragment
import com.brunoponte.dogapp.ui.breedSearchList.BreedSearchListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity()/*, BottomNavigationView.OnNavigationItemSelectedListener*/ {

    lateinit var navController : NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        setNavigationController()

        /*
        val navController = Navigation.findNavController(this, R.id.fragment_container)
        val bottomNavigationView = binding.bottomNavigationView
        setupWithNavController(bottomNavigationView, navController)
*/


        /*
        val breedListFragment = BreedListFragment()
        val breedSearchListFragment = BreedSearchListFragment()

        setCurrentFragment(breedListFragment)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.list -> setCurrentFragment(breedListFragment)
                R.id.search -> setCurrentFragment(breedSearchListFragment)
            }
            true
        }

         */
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun setNavigationController() {
        //Usando a helper class Navigation podemos encontrar o componente do NavController
        //Baseado no NavHostFragment que está hospedado em nosso Layout Principal
        navController = Navigation.findNavController(this, R.id.fragment_container)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.breedListFragment, R.id.breedSearchListFragment -> showBottomNav()
                R.id.breedDetailsFragment -> hideBottomNav()
            }
        }

        //Instanciamos o BottomNavigationView e setamos o controlador
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun showBottomNav() {
        binding.bottomNavigationView.visibility = View.VISIBLE

    }

    private fun hideBottomNav() {
        binding.bottomNavigationView.visibility = View.GONE

    }

    /*
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.breedListFragment -> {
                val fragment = BreedListFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
            }
            R.id.breedSearchListFragment -> {
                val fragment = BreedSearchListFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
            }
        }
        return true
    }

     */
}