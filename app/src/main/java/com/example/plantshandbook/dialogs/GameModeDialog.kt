package com.example.plantshandbook.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.plantshandbook.databinding.GameModeDialogBinding



object GameModeDialog {
        fun showDialog(context: Context, listener: Listener){
            var dialog: AlertDialog? = null
            val builder = AlertDialog.Builder(context)
            val binding = GameModeDialogBinding.inflate(LayoutInflater.from(context))
            builder.setView(binding.root)
            binding.apply {
                btnGameModeFree.setOnClickListener {
                        listener.onClickSwitchFree()
                        dialog?.dismiss()
                }
                btnGameModeLimit.setOnClickListener {
                    listener.onClickSwitchLimit()
                    dialog?.dismiss()
                }

            }
            dialog = builder.create()
            dialog.window?.setBackgroundDrawable(null)
            dialog.show()
        }
        interface Listener{
            fun onClickSwitchFree()
            fun onClickSwitchLimit()
        }
}