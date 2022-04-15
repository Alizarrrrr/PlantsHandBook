package com.example.plantshandbook.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.plantshandbook.databinding.GamePassDialogBinding


object GamePassDialog {
        fun showDialog(context: Context, listener: Listener){
            var dialog: AlertDialog? = null
            val builder = AlertDialog.Builder(context)
            val binding = GamePassDialogBinding.inflate(LayoutInflater.from(context))
            builder.setView(binding.root)
            binding.apply {
                btnSwitchRedact.setOnClickListener {
                        listener.onClickSwitchRedact()
                        dialog?.dismiss()
                }
                btnSwitchMain.setOnClickListener {
                    listener.onClickSwitchMain()
                    dialog?.dismiss()
                }

            }
            dialog = builder.create()
            dialog.window?.setBackgroundDrawable(null)
            dialog.show()
        }
        interface Listener{
            fun onClickSwitchRedact()
            fun onClickSwitchMain()
        }
}