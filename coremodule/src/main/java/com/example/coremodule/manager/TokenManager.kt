package com.example.coremodule.manager

import android.content.Context
import com.example.coremodule.prefs.CoreSharedPref

open class TokenManager(private val context: Context) {

    private val tokenPrefs = TokenPrefs(context)
    private var _mutableToken: String?
    val token: String?
        get() = _mutableToken


    init {
        _mutableToken = tokenPrefs.prefs.getString(TOKEN_KEY, "")
    }


    fun setAuthToken(token: String) {
        this._mutableToken = token
        tokenPrefs.editor.putString(TOKEN_KEY, token).apply()
    }


    fun reset() {
        setAuthToken("")
    }

    inner class TokenPrefs(context: Context) : CoreSharedPref(context)

}


const val TOKEN_KEY = "token_key"