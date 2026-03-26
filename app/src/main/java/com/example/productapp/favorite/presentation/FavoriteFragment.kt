package com.example.productapp.favorite.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.productapp.R
import com.example.productapp.databinding.FragmentFavoriteBinding
import com.example.productapp.detail.presentation.NavigateToDetail
import com.example.productapp.favorite.FavoriteViewModel
import com.example.productapp.product.presentation.ProductAdapter
import com.example.productapp.product.presentation.ProductClickListener
import com.example.productapp.product.presentation.UpdateProductList
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private val viewModel: FavoriteViewModel by viewModel()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoriteBinding.bind(view)

        val adapter = ProductAdapter(object : ProductClickListener {
            override fun click(id: Int) {
                viewModel.openDetail(id)
            }

            override fun onLikeClick(id: Int) {
                viewModel.toggleLike(id)
            }
        })
        binding.productList.adapter = adapter

        val productListWrapper: UpdateProductList = adapter

        viewModel.liveData.observe(viewLifecycleOwner) { state ->
            // 1. Отображаем данные
            state.show(adapter, binding.emptyText)

            // 2. Обрабатываем переход, если UiState — это DetailLoad
            // Но лучше сделать через navigationCommand, как в ProductListFragment
            state.navigateToDetail(object : NavigateToDetail {
                override fun navigateToDetail(productId: Int) {
                    findNavController().navigate(R.id.action_favorite_to_detail)
                    // Здесь тоже желательно добавить сброс стейта во ViewModel
                }
            })
        }

        binding.backButton.setOnClickListener {
            val success = findNavController().popBackStack()

            if (!success) {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
        viewModel.init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}