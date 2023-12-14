package com.sharma.lokalassignment.presentation.helpers

import android.widget.TextView
import androidx.core.content.ContextCompat
import com.sharma.lokalassignment.R

fun TextView.setStock(stock: Int) {
    if (stock > 0) {
        text = "In stock!"
        setTextColor(ContextCompat.getColor(context, R.color.success_green))
    } else {
        setTextColor(ContextCompat.getColor(context, R.color.light_red))
        text = "Out of stock!"
    }
}