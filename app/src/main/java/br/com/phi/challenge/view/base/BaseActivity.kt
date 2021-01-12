package br.com.phi.challenge.view.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import br.com.phi.challenge.BR
import br.com.phi.challenge.R
import br.com.phi.challenge.viewmodel.base.BaseViewModel
import br.com.phi.challenge.viewmodel.base.toolbar.ToolbarViewModel
import org.koin.android.ext.android.inject
import kotlin.reflect.KClass
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by pcamilo on 10/01/2021.
 */
abstract class BaseActivity<VB: ViewDataBinding, VM: BaseViewModel>(clazz : KClass<VM>, @LayoutRes private val layoutId: Int) : AppCompatActivity() {

    lateinit var viewBinding: VB
    val viewModel : VM by viewModel(clazz)
    val toolbarViewModel : ToolbarViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = DataBindingUtil.setContentView<VB>(this, layoutId).apply {
            lifecycleOwner = this@BaseActivity
            setVariable(BR.viewModel, viewModel)
        }

        lifecycle.addObserver(viewModel)

        if (savedInstanceState == null) initActivity()
    }

    override fun onResume() {
        setupToolbar()
        super.onResume()
    }

    /**
     * Setup the toolbar with title and home back button android native
     */
    protected fun setupToolbarTitle(toolbar: Toolbar, homeButtonEnable: Boolean = false){
        delegate.setSupportActionBar(toolbar)
        delegate.supportActionBar?.let {
            it.setDisplayShowTitleEnabled(false)
            it.setDisplayShowHomeEnabled(homeButtonEnable)
            it.setDisplayHomeAsUpEnabled(homeButtonEnable)
        }
    }

    abstract fun initActivity()
    abstract fun setupToolbar()
}