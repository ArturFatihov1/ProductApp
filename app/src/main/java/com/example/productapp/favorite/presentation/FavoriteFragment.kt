package com.example.productapp.favorite.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.productapp.Navigate
import com.example.productapp.R
import com.example.productapp.databinding.FragmentFavoriteBinding
import com.example.productapp.product.presentation.ProductAdapter
import com.example.productapp.product.presentation.ProductClickListener
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
                (requireActivity() as Navigate).navigateToDetail(id)
            }

            override fun onLikeClick(id: Int) {
                viewModel.toggleLike(id)
            }
        })

        binding.productList.adapter = adapter
        viewModel.liveData.observe(viewLifecycleOwner) { state ->
            state.show(adapter, binding.emptyText)
        }

        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        viewModel.init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}