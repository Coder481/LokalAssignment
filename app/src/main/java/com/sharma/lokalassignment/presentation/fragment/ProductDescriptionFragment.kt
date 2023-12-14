package com.sharma.lokalassignment.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.sharma.lokalassignment.R
import com.sharma.lokalassignment.databinding.FragmentProductDescriptionBinding
import com.sharma.lokalassignment.domain.model.Product
import com.sharma.lokalassignment.presentation.activity.MainActivity
import com.sharma.lokalassignment.presentation.adapter.ImagePagerAdapter
import com.sharma.lokalassignment.presentation.helpers.ImageLoadHelper
import com.sharma.lokalassignment.presentation.viewModel.MainViewModel
import kotlinx.coroutines.launch
import kotlin.math.floor

class ProductDescriptionFragment: Fragment() {

    private lateinit var binding: FragmentProductDescriptionBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDescriptionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setOnClickListener {  }
        val productId = arguments?.getInt(MainActivity.PRODUCT_ID_KEY)
        productId?.let {
            val product = viewModel.getProduct(it)
            showProductData(product)
        }
    }

    private fun showProductData(product: Product?) {
        product?.let {
            setUpImageAdapter(product.images)
            binding.viewProductDescription.apply {
                // Enter product description fields
                tvProductName.text = product.title
                tvProductBrand.text = "A ${product.brand} product"
                tvProductDescription.text = product.description
                viewDiscount.setText("${floor(product.discountPercentage).toInt()}+% Off")
                viewPrice.setText("${product.price}/-")
                viewRating.setText("${product.rating}/5")

                // Stock items with color coding
                var stockTextColor = resources.getColor(R.color.success_green, null)
                val stockText =
                if (product.stock > 0) {
                    "${product.stock} items left"
                } else {
                    stockTextColor = resources.getColor(R.color.light_red, null)
                    "No items left"
                }
                viewStock.apply {
                    setText(stockText)
                    setTextColor(stockTextColor)
                }
            }

            binding.backPressBtn.setOnClickListener {
                (activity as? MainActivity)?.removeFragment()
            }
        }
    }

    private fun setUpImageAdapter(images: List<String>) {
        val adapter = ImagePagerAdapter(images).also {
            it.setUpListener(object : ImagePagerAdapter.ImageItemListener{
                override fun loadImage(imageUrl: String, imageView: ImageView) {
                    lifecycleScope.launch {
                        try {
                            val bitmap = ImageLoadHelper.loadImage(imageUrl, requireContext())
                            imageView.setImageBitmap(bitmap)
                        } catch (e: Exception) {
                            imageView.setImageResource(R.drawable.ic_error_image)
                        }
                    }
                }
            })
        }
        binding.viewProductDescription.viewPager.apply {
            this.adapter = adapter
            binding.viewProductDescription.dotsIndicator.attachTo(this)
        }
    }
}