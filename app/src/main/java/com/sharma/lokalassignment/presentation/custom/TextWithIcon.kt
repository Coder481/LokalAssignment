package com.sharma.lokalassignment.presentation.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.sharma.lokalassignment.R

class TextWithIcon @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
): ConstraintLayout(context, attrs, defStyle) {

    private val icon: ImageView
    private val textView: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.view_text_with_icon, this, true)

        // Find views
        textView = findViewById(R.id.tv_text)
        icon = findViewById(R.id.iv_icon)

        // Retrieve attributes from XML (if any)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextWithIcon)
        val text = typedArray.getString(R.styleable.TextWithIcon_text)
        val iconResId = typedArray.getResourceId(R.styleable.TextWithIcon_icon, 0)
        typedArray.recycle()

        setText(text)
        setIcon(iconResId)
    }

    private fun setIcon(iconResId: Int) {
        icon.setImageResource(iconResId)
    }

    fun setText(text: String?) {
        text?.let { textView.text = it }
    }

    fun setTextColor(color: Int) {
        textView.setTextColor(color)
    }
}