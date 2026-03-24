package com.example.productapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.productapp.detail.presentation.NavigateToDetail
import com.example.productapp.favorite.NavigateToFavorite
import com.example.productapp.load.presentation.NavigateToLoad
import com.example.productapp.product.presentation.NavigateToProductList

class MainActivity : AppCompatActivity(), Navigate {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun navigateToProductList() {
        navController.navigate(R.id.action_load_to_productList)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun navigateToDetail(productId: Int) {
        navController.navigate(R.id.action_productList_to_detail)
    }

    override fun navigateToFavorite() {
        navController.navigate(R.id.action_productList_to_favorite)
    }

    override fun navigateToLoad() {
        navController.navigate(R.id.loadFragment)
    }
}

interface Navigate : NavigateToProductList, NavigateToDetail, NavigateToLoad, NavigateToFavorite