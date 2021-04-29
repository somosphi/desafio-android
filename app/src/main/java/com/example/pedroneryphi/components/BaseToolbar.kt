package com.example.pedroneryphi.components

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import com.example.pedroneryphi.R
import kotlinx.android.synthetic.main.base_toolbar.view.*

class BaseToolbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.base_toolbar, this, true)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseToolbar)
        setToolbarHeader(typedArray)
        setBackArrow(typedArray)
        typedArray.recycle()
    }

    private fun setToolbarHeader(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.BaseToolbar_toolbarHeader)) {
            val toolbarHeader = typedArray.getString(R.styleable.BaseToolbar_toolbarHeader) ?: ""
            tvTitle.text = toolbarHeader
        }
    }

    private fun setBackArrow(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.BaseToolbar_toolbarBack)) {
            val toolbarBack = typedArray.getBoolean(R.styleable.BaseToolbar_toolbarBack, false)
            if (toolbarBack) {
                arrowBack.visibility = View.VISIBLE
            }
        }
        arrowBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}