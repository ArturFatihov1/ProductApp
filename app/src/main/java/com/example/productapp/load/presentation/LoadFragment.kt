package com.example.productapp.load.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.productapp.R
import com.example.productapp.databinding.FragmentLoadingBinding
import com.example.productapp.product.presentation.NavigateToProductList
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoadFragment : Fragment(R.layout.fragment_loading) {

    private val viewModel: LoadViewModel by viewModel()
    private var _binding: FragmentLoadingBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoadingBinding.bind(view)


        viewModel.liveData.observe(viewLifecycleOwner) { uiState ->
            uiState.show(
                binding.errorText,
                binding.retryButton,
                binding.progressBar
            )
            uiState.navigate(requireActivity() as NavigateToProductList)
        }

        binding.retryButton.setOnClickListener {
            viewModel.load()
        }

        viewModel.load(isFirstRun = savedInstanceState == null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}