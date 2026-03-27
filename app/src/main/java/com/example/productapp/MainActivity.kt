package com.example.productapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.productapp.detail.data.DetailRepository
import com.example.productapp.detail.presentation.NavigateToDetail
import com.example.productapp.favorite.presentation.NavigateToFavorite
import com.example.productapp.load.presentation.NavigateToLoad
import com.example.productapp.product.presentation.NavigateToProductList
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), Navigate {

    private lateinit var navController: NavController
    private val detailRepository: DetailRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nav_host_fragment)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun navigateToProductList() {
        navController.navigate(R.id.productListFragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun navigateToDetail(productId: Int) {
        val bundle = Bundle().apply {
            putInt("productId", productId)
        }
        navController.navigate(R.id.detailFragment, bundle)
    }

    override fun navigateToFavorite() {
        navController.navigate(R.id.favoriteFragment)
    }

    override fun navigateToLoad() {
        navController.navigate(R.id.loadFragment)
    }
}

interface Navigate : NavigateToProductList, NavigateToDetail, NavigateToLoad, NavigateToFavorite