package br.com.andreviana.phi.desafioandroid.util.helper

import android.content.Context
import android.content.SharedPreferences
import br.com.andreviana.phi.desafioandroid.data.common.Constants.PREFERENCES_NAME

class PreferencesHelper(var context: Context) {

    private var preferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    private var editor = preferences.edit()

    fun setVisibilityBalance(visibility: Boolean) {
        editor.putBoolean("visibilityBalance", visibility)
        editor.commit()
    }

    fun getVisibilityBalance(): Boolean = preferences.getBoolean("visibilityBalance", false)
}