package com.example.greenkim.api.auth

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Prefs(context: Context) {
    private val prefName = "tokenPref"
    private val prefs = context.getSharedPreferences(prefName,MODE_PRIVATE)
    var token:String?
        get() = prefs.getString("",null)
        set(value) {
            prefs.edit().putString("",value).commit()
        }

}
