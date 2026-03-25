package com.example.productapp.detail.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.productapp.R
import com.example.productapp.databinding.FragmentDetailBinding
import com.example.productapp.detail.DetailViewModel
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
                binding.title, binding.description, binding.price,
                binding.stock, binding.imageDetail, binding.recipeLike
            )
        }

        // КНОПКА НАЗАД: Теперь она точно сработает
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.recipeLike.setOnClickListener { viewModel.toggleLike() }

        viewModel.init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}