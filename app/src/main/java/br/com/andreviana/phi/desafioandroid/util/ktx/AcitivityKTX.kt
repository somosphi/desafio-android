package br.com.andreviana.phi.desafioandroid.util.ktx

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import br.com.andreviana.phi.desafioandroid.data.common.Constants.STATEMENT_ID
import br.com.andreviana.phi.desafioandroid.ui.statement.detail.StatementDetailActivity

fun Activity.navigationToStatementDetail(id: String) {
    val bundle = Bundle().apply { this.putString(STATEMENT_ID, id) }
    this.startActivity(Intent(this, StatementDetailActivity::class.java).putExtras(bundle))
}

fun Activity.showToastLong(message: String) {
    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
}

fun Activity.showToastShort(message: String) {
    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
}
