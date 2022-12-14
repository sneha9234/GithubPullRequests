package com.example.pullrequests.basefiles

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.pullrequests.R
import com.example.pullrequests.networkConnection.ConnectionLiveData
import com.example.pullrequests.utils.CommonDialog
import com.example.networkmodule.core.DEFAULT_ERROR_MESSAGE
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    private var alertDialog: Dialog? = null
    private var connectionLiveData: ConnectionLiveData? = null
    private var needToShowInternetBack: Boolean = false

    override fun onResume() {
        super.onResume()
        connectionLiveData = ConnectionLiveData(this)
        connectionLiveData?.observe(this) { isNetworkAvailable ->
            if (isNetworkAvailable == true) {
                if (needToShowInternetBack) {
                    needToShowInternetBack = false
                    showSnackbar(R.string.internet_available, Color.GREEN)
                }
            } else {
                needToShowInternetBack = true
                showInternetError()
            }
        }
    }

    private fun showSnackbar(text:Int, color:Int) {
        val parentLayout: View? = this.findViewById(android.R.id.content)
        parentLayout?.let {
            Snackbar.make(it, text, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(color)
                .show()
        }
    }

    fun showLoading() {
        if (alertDialog != null) {
            alertDialog?.show()
        } else {
            alertDialog = Dialog(this)
            alertDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alertDialog?.setContentView(R.layout.progressbar)
            alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog?.show()
            alertDialog?.setCancelable(false)
        }
    }

    fun dismissLoading() {
        if (alertDialog?.isShowing==true)
            alertDialog?.dismiss()
    }


    fun showError(content: String?, error: String? = getString(R.string.error)) {
        if (content != null && content.isEmpty()) {
            return
        }
        CommonDialog.show(this) {
            title = error
            message = content ?: DEFAULT_ERROR_MESSAGE
            positiveButton(getString(R.string.ok))
        }
    }

    private fun showInternetError() {
        showSnackbar(R.string.internet_unavailable, Color.RED)
    }
}