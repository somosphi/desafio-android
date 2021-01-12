package br.com.phi.challenge.utils

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.phi.challenge.R

/**
 * Created by pcamilo on 10/01/2021.
 */
const val ONE_DIRECTION = 1
const val PIX = "PIX"

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("setAdapter")
    fun RecyclerView.setAdapter(rvAdapter: RecyclerView.Adapter<*>?) {
        rvAdapter?.let { adapter ->
            this.layoutManager = LinearLayoutManager(this.context)
            this.adapter = adapter
            this.adapter?.notifyDataSetChanged()
        }
    }

    @JvmStatic
    @BindingAdapter("reverseVisibility")
    fun View.setReverseVisibility(isVisible: Int) {
        this.visibility = if (isVisible == VISIBLE) GONE else VISIBLE
    }

    @JvmStatic
    @BindingAdapter("setPixVisible")
    fun View.setPixVisible(statementPix: String?) {
        statementPix?.let { pix ->
            this.visibility = if(pix.statementIsPix()) VISIBLE else GONE
        }
    }

    @JvmStatic
    @BindingAdapter("setPixStatementBackground")
    fun View.setPixStatementBackground(statementPix: String?) {
        statementPix?.let { pix ->
            this.setBackgroundColor(if(pix.statementIsPix()) ContextCompat.getColor(this.context, R.color.gray_200) else ContextCompat.getColor(this.context, R.color.white))
        }
    }

    @JvmStatic
    @BindingAdapter( "loadMoreItems")
    fun RecyclerView.setLoadMoreItems(requestLoadMore: () -> Unit) {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(ONE_DIRECTION)) {
                    requestLoadMore.invoke()
                }
            }
        })
    }

    @JvmStatic
    @BindingAdapter( "changeIconEye")
    fun ImageView.changeIconEye(isEyeOpen: Int) {
        this.setImageResource(if (isEyeOpen == GONE) R.drawable.eye_close else R.drawable.eye_open)
    }
}