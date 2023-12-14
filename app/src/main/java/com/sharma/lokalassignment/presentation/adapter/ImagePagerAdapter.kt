package com.sharma.lokalassignment.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sharma.lokalassignment.databinding.ViewPagerItemBinding

class ImagePagerAdapter constructor(private val images: List<String>): RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder>() {

    private var itemListener: ImageItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val b = ViewPagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(b)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position], itemListener)
    }

    fun setUpListener(listener: ImageItemListener) {
        itemListener = listener
    }

    interface ImageItemListener {
        fun loadImage(imageUrl: String, imageView: ImageView)
    }

    inner class ImageViewHolder(private val binding: ViewPagerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String, itemListener: ImageItemListener?) {
            itemListener?.loadImage(imageUrl, binding.root)
        }
    }
}