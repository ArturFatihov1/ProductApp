package com.example.productapp.product.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.productapp.R
import com.example.productapp.databinding.FragmentProductListBinding
import com.example.productapp.product.ProductListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListFragment : Fragment(R.layout.fragment_product_list) {

    private val viewModel: ProductListViewModel by viewModel()
    private var searchJob: kotlinx.coroutines.Job? = null
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductListBinding.bind(view)

        val adapter = ProductAdapter(object : ProductClickListener {
            override fun click(id: Int) {
                viewModel.openDetail(id)
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

        viewModel.navigationCommand.observe(viewLifecycleOwner) { id ->
            id?.let {
                findNavController().navigate(R.id.action_productList_to_detail)
                viewModel.onNavigationDone()
            }
        }

        binding.filterButton.setOnClickListener {
            val categories = viewModel.categoriesLiveData.value
            if (categories.isNullOrEmpty()) {
                return@setOnClickListener
            }

            val categoryNames = categories.map { it.name }.toTypedArray()

            android.app.AlertDialog.Builder(requireContext())
                .setTitle("Выберите категорию")
                .setItems(categoryNames) { dialog, which ->
                    val selectedCategory = categories[which]
                    viewModel.filterByCategory(selectedCategory.slug)
                }
                .show()
        }


        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchJob?.cancel()

                searchJob = viewLifecycleOwner.lifecycleScope.launch {
                    kotlinx.coroutines.delay(500)
                    viewModel.search(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.favoriteButton.setOnClickListener {
            findNavController().navigate(R.id.action_productList_to_favorite)
        }

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