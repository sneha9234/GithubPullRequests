package com.example.coremodule.prefs

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

open class CoreSharedPref(context: Context) {
    val prefs: SharedPreferences =
        context.getSharedPreferences(CoreSharedPref::class.java.simpleName, Activity.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = prefs.edit()

    fun contains(key: String) = prefs.contains(key)



    fun getValue(key: String): String {
        return prefs.getString(key, "")!!
    }


}