package com.example.plantshandbook.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.plantshandbook.databinding.CloseAppDialogBinding


object CloseAppDialog {
    fun showDialog(context: Context, listener: Listener){
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = CloseAppDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.apply {
            btnCloseApp.setOnClickListener {
                listener.onClick()
                dialog?.dismiss()
            }
            btnCloseAppCancel.setOnClickListener {
                dialog?.dismiss()
            }
        }
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }
    interface Listener{
        fun onClick()
    }
}