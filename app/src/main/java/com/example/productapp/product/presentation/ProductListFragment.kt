package com.example.productapp.product.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.productapp.Navigate
import com.example.productapp.R
import com.example.productapp.databinding.FragmentProductListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListFragment : Fragment(R.layout.fragment_product_list) {

    private val viewModel: ProductListViewModel by viewModel()
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductListBinding.bind(view)

        val categoryDialog = CategoryDialog(requireContext())

        val adapter = ProductAdapter(object : ProductClickListener {
            override fun click(id: Int) {
                Log.d("ProductListFragment", "Passing click to Activity: $id")

                (activity as? Navigate)?.navigateToDetail(id)
            }

            override fun onLikeClick(id: Int) {
                viewModel.toggleLike(id)
            }
        })
        binding.productList.adapter = adapter
        viewModel.liveData.observe(viewLifecycleOwner) { uiState ->
            uiState.update(
                binding.searchInput,
                adapter,
                binding.favoriteCountTextView
            )
        }

        binding.favoriteButton.setOnClickListener {
            (requireActivity() as Navigate).navigateToFavorite()
        }

        binding.filterButton.setOnClickListener {
            val categories = viewModel.categoriesLiveData.value ?: return@setOnClickListener
            categoryDialog.show(categories) { slug ->
                viewModel.filterByCategory(slug)
            }
        }

        binding.searchInput.addTextChangedListener(
            SearchWatcher(viewLifecycleOwner.lifecycleScope) { query ->
                viewModel.search(query)
            }
        )

        viewModel.init()
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchProducts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}