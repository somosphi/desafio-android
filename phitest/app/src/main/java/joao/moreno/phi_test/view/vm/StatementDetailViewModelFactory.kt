package joao.moreno.phi_test.view.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StatementDetailViewModelFactory(
    var transactionId : String
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val trasactionID = this.transactionId
        return StatementDetailViewModel(trasactionID) as T
    }
}