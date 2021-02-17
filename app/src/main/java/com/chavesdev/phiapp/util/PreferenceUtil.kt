package com.chavesdev.phiapp.util

import android.content.Context
import android.preference.PreferenceManager

object PreferenceUtil {

    fun isBalanceVisible(context: Context): Boolean =
        getBooleanPref(context, Constants.Preferences.balanceVisibility, false)

    fun saveStringPreference(context: Context, prefKey: String, value: String?) {
        getPrefs(context)
            .edit()
            .putString(prefKey, value)
            .apply()
    }

    fun saveBooleanPref(context: Context, prefKey:String, value: Boolean) {
        getPrefs(context)
            .edit()
            .putBoolean(prefKey, value)
            .apply()
    }

    private fun getBooleanPref(context: Context, prefKey: String, defaultValue: Boolean): Boolean =
        getPrefs(context).getBoolean(prefKey, defaultValue)

    private fun getPrefs(context: Context) = PreferenceManager.getDefaultSharedPreferences(context)
}