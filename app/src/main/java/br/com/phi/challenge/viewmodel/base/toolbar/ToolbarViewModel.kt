package br.com.phi.challenge.viewmodel.base.toolbar

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

/**
 * Created by pcamilo on 10/01/2021.
 */
class ToolbarViewModel(private val context: Context) : ViewModel() {

    var toolbarTitle = ObservableField<String>()

    fun setToolbar(toolbar: Toolbar) {
        toolbarTitle.set(context.getString(toolbar.title))
    }
}