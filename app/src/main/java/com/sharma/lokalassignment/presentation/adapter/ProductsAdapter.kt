package com.sharma.lokalassignment.presentation.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sharma.lokalassignment.databinding.ViewProductBinding
import com.sharma.lokalassignment.domain.model.Product
import com.sharma.lokalassignment.domain.model.Products
import com.sharma.lokalassignment.presentation.helpers.setStock
import kotlin.math.floor

class ProductsAdapter (private val products: Products): RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    private var clickListener: ProductItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val b = ViewProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(b)
    }

    override fun getItemCount(): Int = products.products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products.products[position], clickListener)
    }

    fun setListener(listener: ProductItemListener) = run { clickListener = listener }

    interface ProductItemListener {
        fun onClick(productId: Int)
        fun loadImage(url: String, imageView: ImageView)
    }

    class ProductViewHolder(private val binding: ViewProductBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product, clickListener: ProductItemListener?) {
            binding.apply {
                tvProductName.text = product.title
                tvProductPrice.text = "₹${product.price}"
                tvProductRating.text = "${product.rating}/5⭐️"
                tvProductStock.setStock(product.stock)
                tvProductDiscount.text = "${floor(product.discountPercentage).toInt()}+% Off"
                clickListener?.let {
                    root.setOnClickListener { clickListener.onClick(product.id) }
                    it.loadImage(product.thumbnail, ivProductImage)
                }
            }
        }

    }

}