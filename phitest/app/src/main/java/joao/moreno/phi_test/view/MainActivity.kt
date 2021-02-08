package joao.moreno.phi_test.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import joao.moreno.phi_test.*
import joao.moreno.phi_test.databinding.ActivityMainBinding
import joao.moreno.phi_test.utils.ViewUtils
import joao.moreno.phi_test.utils.view.DialogError
import joao.moreno.phi_test.view.adapter.StatementAdapter
import joao.moreno.phi_test.view.vm.BalanceViewModel


class MainActivity : AppCompatActivity() {

    private val viewModel: BalanceViewModel by lazy {
        ViewModelProvider(this).get(BalanceViewModel::class.java)
    }

    lateinit var binding: ActivityMainBinding

    companion object {
        const val KEY_INTENT = "STATEMENT_TO_DETAIL"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel.balanceLiveData.observe(
            this,
            { binding.labelBalanceValue.text = "${ViewUtils.formatValueCurrency(it.amount)}" })
        viewModel.statementLiveData.observe(this, {
            setupLoading(false)
            binding.rvStatement.adapter = StatementAdapter(it.items!!, this) {
                val intent = Intent(this, StatementDetailActivity::class.java).apply {
                    putExtra(KEY_INTENT, it)
                }
                startActivity(intent)
            }
        })
        viewModel.error.observe(this, { error ->
            if (error) {
                DialogError(this) {}.show()
            }
        })
        viewModel.stateVisibilityBalance.observe(this, { isVisible ->
            if (isVisible) {
                setupBalanceVisible(binding)
            } else {
                setupBalanceHide(binding)
            }
        })

        binding.cardView.setOnClickListener {
            if (binding.labelBalanceValue.isVisible) {
                setupBalanceHide(binding)
            } else {
                setupBalanceVisible(binding)
            }
        }
    }

    fun setupLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.frameLoading.startShimmer()
            binding.frameLoading.visibility = View.VISIBLE
            binding.rvStatement.visibility = View.GONE
        } else {
            binding.frameLoading.stopShimmer()
            binding.frameLoading.visibility = View.GONE
            binding.rvStatement.visibility = View.VISIBLE
        }
    }

    fun setupBalanceVisible(binding: ActivityMainBinding) {
        binding.labelBalanceValue.visibility = View.VISIBLE
        binding.shapeHideBalance.visibility = View.GONE
        binding.iconVisibilityBalance.setImageDrawable(resources.getDrawable(R.drawable.icon_hide_balance))
        binding.iconVisibilityBalance.contentDescription = getString(R.string.activity_main_hide_balance_accessibility)
        viewModel.saveStateVisibilityBalance(true)
    }


    fun setupBalanceHide(binding: ActivityMainBinding) {
        binding.labelBalanceValue.visibility = View.GONE
        binding.shapeHideBalance.visibility = View.VISIBLE
        binding.iconVisibilityBalance.setImageDrawable(resources.getDrawable(R.drawable.icon_see_balance))
        binding.iconVisibilityBalance.contentDescription = getString(R.string.activity_main_show_balance_accessibility)
        viewModel.saveStateVisibilityBalance(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.compositeDisposable.dispose()
    }

}