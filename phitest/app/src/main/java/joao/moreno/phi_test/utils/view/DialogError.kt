package joao.moreno.phi_test.utils.view

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.Window
import com.google.android.material.button.MaterialButton
import joao.moreno.phi_test.R

class DialogError(
    context: Context,
    cancelListener: DialogInterface.OnCancelListener?
) : Dialog(context, false, cancelListener) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dilaog_error_layout)
        setupView()
    }

    private fun setupView() {
        val btnCloseDialog : MaterialButton = findViewById(R.id.btn_close_dialog_error)
        setupListeners(btnCloseDialog)
    }

    private fun setupListeners(view : View){
        view.setOnClickListener { this.dismiss() }
    }
}