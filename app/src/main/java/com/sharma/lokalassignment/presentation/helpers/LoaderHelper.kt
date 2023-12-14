package com.sharma.lokalassignment.presentation.helpers

import android.app.AlertDialog
import android.content.Context

object LoaderHelper {

    private var dialog: AlertDialog? = null
    fun showLoader() {
        dialog?.let {
            if (it.isShowing.not()) it.show()
        }
    }

    fun hideLoader() {
        dialog?.let {
            if (it.isShowing) {
                it.hide()
            }
        }
    }

    fun Builder(context: Context): LoaderHelper {
        dialog = AlertDialog.Builder(context)
            .setCancelable(false)
            .setMessage("Please wait! Loading..")
            .create()
        return this
    }
}