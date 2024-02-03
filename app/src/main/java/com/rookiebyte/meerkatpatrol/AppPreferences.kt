package com.rookiebyte.meerkatpatrol

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(val context: Context) {
    private val URL_KEY = "url"

    fun getUrl(): String? {
        return getPrivate()?.getString(URL_KEY, null)
    }

    fun setUrl(value: String) {
        getPrivate()?.edit()?.putString(URL_KEY, value)?.apply()
    }

    private fun getPrivate(): SharedPreferences? {
        return context.getSharedPreferences(
            "com.rookiebyte.meerkatpatrol.preferences",
            Context.MODE_PRIVATE
        )
    }
}
