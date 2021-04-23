package com.henrique.desafio_android.view.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.res.use
import androidx.databinding.BindingAdapter
import com.henrique.desafio_android.R

class MyLoader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(
    context,
    attrs,
    defStyleAttr
) {

    lateinit var loaderProgress: ProgressBar
    lateinit var loaderText: TextView
    lateinit var loaderRetry: Button

    init {
        inflateLayout()
        bindView()
        loadAttributes(attrs, defStyleAttr)
    }

    private fun inflateLayout() {
        LayoutInflater
            .from(context)
            .inflate(R.layout.view_my_loader, this, true)
    }

    fun bindView() {
        loaderText = findViewById(R.id.loaderText)
        loaderRetry = findViewById(R.id.loaderRetry)
        loaderProgress = findViewById(R.id.loaderProgressBar)
    }

    private fun loadAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        context
            .theme
            .obtainStyledAttributes(
                attrs,
                R.styleable.MyLoader,
                defStyleAttr,
                R.style.MyLoaderStyle
            )
            .use {
                setState(
                    State.valueOf(it.getInt(R.styleable.MyLoader_loaderState, State.ERROR.value))
                )
            }
    }

    enum class State(val value: Int) {
        COMPLETE(0),
        ERROR(1),
        LOADING(2);

        internal companion object {
            internal fun valueOf(id: Int): State {
                for (f in values()) {
                    if (f.value == id) return f
                }
                throw IllegalArgumentException()
            }
        }
    }
}

@BindingAdapter("loaderState")
fun MyLoader.setState(state: MyLoader.State? = null) {
    state?.let {
        when (state) {
            MyLoader.State.COMPLETE -> {
                visibility = View.GONE
            }
            MyLoader.State.ERROR -> {
                visibility = View.VISIBLE
                loaderRetry.visibility = View.VISIBLE
                loaderProgress.visibility = View.GONE
                loaderText.text = context.getString(R.string.my_loader_error_text)
            }
            MyLoader.State.LOADING -> {
                visibility = View.VISIBLE
                loaderRetry.visibility = View.GONE
                loaderProgress.visibility = View.VISIBLE
                loaderText.text = context.getString(R.string.my_loader_loading_text)
            }
        }
    }
}

@BindingAdapter("retryAction")
fun MyLoader.setRetryAction(onClickListener: View.OnClickListener? = null) {
    loaderRetry.setOnClickListener(onClickListener)
}