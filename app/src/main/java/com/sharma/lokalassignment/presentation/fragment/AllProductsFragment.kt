package com.sharma.lokalassignment.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sharma.lokalassignment.R
import com.sharma.lokalassignment.presentation.activity.MainActivity
import com.sharma.lokalassignment.databinding.FragmentAllProductsBinding
import com.sharma.lokalassignment.domain.model.Products
import com.sharma.lokalassignment.presentation.adapter.ProductsAdapter
import com.sharma.lokalassignment.presentation.helpers.ImageLoadHelper
import com.sharma.lokalassignment.presentation.helpers.LoaderHelper
import com.sharma.lokalassignment.presentation.helpers.collectLifeCycleAware
import com.sharma.lokalassignment.presentation.helpers.gone
import com.sharma.lokalassignment.presentation.helpers.visible
import com.sharma.lokalassignment.presentation.mapper.MainUiState
import com.sharma.lokalassignment.presentation.viewModel.MainViewModel
import kotlinx.coroutines.launch

class AllProductsFragment: Fragment() {

    private lateinit var binding: FragmentAllProductsBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllProductsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllProducts()
        listenUiState()
    }

    private fun listenUiState() {
        viewModel.uiState.collectLifeCycleAware(viewLifecycleOwner) { state ->
            when(state) {
                is MainUiState.Idle -> LoaderHelper.hideLoader()
                is MainUiState.Loading -> {
                    LoaderHelper.showLoader()
                }
                is MainUiState.Success -> {
                    LoaderHelper.hideLoader()
                    setUpProducts(state.data)
                }
                is MainUiState.Failure -> {
                    LoaderHelper.hideLoader()
                    showError(state.errorMessage)
                }
            }
        }
    }

    private fun setUpProducts(data: Products?) {
        data?.let {
            // make recycler view visible
            showRecyclerView()

            // set up adapter for products
            val adapter = ProductsAdapter(it).also { adapter ->
                adapter.setListener(object : ProductsAdapter.ProductItemListener{
                    override fun onClick(productId: Int) {
                        addFragment(productId)
                    }

                    override fun loadImage(url: String, imageView: ImageView) {
                        lifecycleScope.launch {
                            try {
                                val bitmap = ImageLoadHelper.loadImage(url, requireContext())
                                imageView.setImageBitmap(bitmap)
                            } catch (e: Exception) {
                                imageView.setImageResource(R.drawable.ic_error_image)
                            }
                        }
                    }
                })
            }
            binding.rvProducts.apply {
                this.adapter = adapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun addFragment(productId: Int) {
        (activity as? MainActivity)?.addFragment(ProductDescriptionFragment(), productId)
    }

    private fun showRecyclerView() {
        binding.apply {
            llError.gone()
            rvProducts.visible()
        }
    }

    private fun showError(msg: String) {
        binding.apply {
            llError.visible()
            tvErrorMsg.text = msg
            btnRetry.setOnClickListener { viewModel.getAllProducts() }
            rvProducts.gone()
        }
    }

}