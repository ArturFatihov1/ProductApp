package com.example.productapp.di


import android.content.Context
import com.example.productapp.core.IntCache
import com.example.productapp.core.RunAsync
import com.example.productapp.detail.data.DetailRepository
import com.example.productapp.detail.presentation.DetailViewModel
import com.example.productapp.favorite.data.FavoriteRepository
import com.example.productapp.favorite.presentation.FavoriteViewModel
import com.example.productapp.load.cache.CacheModule
import com.example.productapp.load.cloud.ProductCloudDataSource
import com.example.productapp.load.cloud.ProductService
import com.example.productapp.load.data.LoadRepository
import com.example.productapp.load.presentation.LoadViewModel
import com.example.productapp.product.data.ProductRepository
import com.example.productapp.product.presentation.ProductListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single { get<Context>().getSharedPreferences("products_prefs", Context.MODE_PRIVATE) }
    single<IntCache> { IntCache.Base(sharedPreferences = get(), key = "current_product_id") }

    single<RunAsync> { RunAsync.Base() }

    single {
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductService::class.java)
    }

    single<ProductCloudDataSource> { ProductCloudDataSource.Base(get()) }

    single<CacheModule> { CacheModule.Base(get()) }
    single { get<CacheModule>().dao() }


    single<LoadRepository> { LoadRepository.Base(get(), get()) }
    single<ProductRepository> { ProductRepository.Base(dao = get(), service = get()) }
    single<FavoriteRepository> { FavoriteRepository.Base(get()) }
    single<DetailRepository> { DetailRepository.Base(dao = get(), productIdCache = get()) }

    viewModel { LoadViewModel(get(), get()) }
    viewModel {
        ProductListViewModel(
            repository = get(),
            runAsync = get(),
        )
    }
    viewModel { FavoriteViewModel(get(), get()) }
    viewModel { DetailViewModel(repository = get(), runAsync = get()) }
}