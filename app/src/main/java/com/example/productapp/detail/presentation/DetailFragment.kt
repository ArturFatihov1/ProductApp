package com.example.productapp.detail.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.productapp.R
import com.example.productapp.databinding.FragmentDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by viewModel()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)

        viewModel.liveData.observe(viewLifecycleOwner) { uiState ->
            uiState.show(
                titleView = binding.title,
                descriptionView = binding.description,
                priceView = binding.price,
                stockStatusView = binding.stock,
                imagesView = binding.imageDetail,
                likeIcon = binding.recipeLike
            )
        }

        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.recipeLike.setOnClickListener {
            viewModel.toggleLike()
        }

        viewModel.init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}