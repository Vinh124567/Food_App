package com.example.myapplication.SharedPreferences

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        private const val PREFERENCES_NAME = "my_preferences"
        private const val KEY_ID = "key_id"

        @Volatile
        private var instance: PreferencesManager? = null

        fun getInstance(context: Context): PreferencesManager =
            instance ?: synchronized(this) {
                instance ?: PreferencesManager(context).also { instance = it }
            }
    }

    fun setId(id: Int) {
        editor.putInt(KEY_ID, id)
        editor.apply()
    }

    fun getId(): Int {
        return sharedPreferences.getInt(KEY_ID, -1)
    }
}
