package com.example.myapplication.View

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.View.viewmodel.AuthViewModel
import com.example.myapplication.View.viewmodel.FoodViewModel
import com.example.myapplication.View.viewmodel.OrderViewModel
import com.example.myapplication.View.viewmodel.ViewModelProvider.NewsViewModelProviderFactory
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.repository.NewsRemoteRepository

class MainActivity : AppCompatActivity() {
    lateinit var foodViewModel: FoodViewModel
    lateinit var orderViewModel: OrderViewModel
    lateinit var authViewModel: AuthViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val newsRemoteRepository = NewsRemoteRepository()
        val viewModelProviderFactory =
            NewsViewModelProviderFactory(application, newsRemoteRepository)


        foodViewModel = ViewModelProvider(this, viewModelProviderFactory)[FoodViewModel::class.java]
        orderViewModel = ViewModelProvider(this, viewModelProviderFactory)[OrderViewModel::class.java]
        authViewModel = ViewModelProvider(this, viewModelProviderFactory)[AuthViewModel::class.java]

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.foodNavHostFragment) as NavHostFragment
        val navHostController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navHostController)

        navHostController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> binding.bottomNavigationView.visibility = View.VISIBLE
                R.id.signUpFragment -> binding.bottomNavigationView.visibility = View.GONE
                R.id.addressFragment -> binding.bottomNavigationView.visibility = View.GONE
                R.id.singUpSuccessFragment -> binding.bottomNavigationView.visibility = View.GONE
                R.id.foodDetailFragment2 -> binding.bottomNavigationView.visibility = View.GONE
                R.id.paymentFragment -> binding.bottomNavigationView.visibility = View.GONE
                R.id.orderSuccessFragment -> binding.bottomNavigationView.visibility = View.GONE
                R.id.homeFragment-> binding.bottomNavigationView.visibility = View.VISIBLE
                R.id.yourOrdersFragment-> binding.bottomNavigationView.visibility = View.VISIBLE

            }
        }

        }
}
