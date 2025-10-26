package net.baudovalentim.motivation.preferences

import android.content.Context
import androidx.core.content.edit

class SecurityPreferences(context: Context) {
    private val shared = context.getSharedPreferences("motivation", Context.MODE_PRIVATE)

    fun storeString(key: String, str: String) {
        shared.edit { putString(key, str) }
    }

    fun getString(key: String): String {
        return shared.getString(key, "") ?: ""
    }
}