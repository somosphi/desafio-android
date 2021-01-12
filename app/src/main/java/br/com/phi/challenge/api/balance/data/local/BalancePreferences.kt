package br.com.phi.challenge.api.balance.data.local

import android.content.SharedPreferences
import android.view.View.VISIBLE
import br.com.phi.challenge.di.BALANCE_PREF
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

/**
 * Created by pcamilo on 11/01/2021.
 */
class BalancePreferences : KoinComponent {

    private val preferences: SharedPreferences by inject(named(BALANCE_PREF))

    var balanceIsVisible: Int
        get() = preferences.getInt(IS_BALANCE_VISIBLE, VISIBLE)
        set(isVisible) = preferences.edit().putInt(IS_BALANCE_VISIBLE, isVisible).apply()

    companion object {
        private const val IS_BALANCE_VISIBLE = "balance_is_visible"
    }
}