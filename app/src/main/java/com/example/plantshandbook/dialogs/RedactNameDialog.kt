package com.example.plantshandbook.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.plantshandbook.databinding.RedactNameDialogBinding

object RedactNameDialog {

    fun showDialog(context: Context, listener: Listener, name: String){
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = RedactNameDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.apply {
            edRenameImage.setText(name)
            btnRename.setOnClickListener {
                val listName = edRenameImage.text.toString()
                if(listName.isNotEmpty()){
                    listener.onClick(listName)
                    dialog?.dismiss()
                }else
                {
                    dialog?.dismiss()
                }
            }
            btnCancelRedact.setOnClickListener {
                dialog?.dismiss()
            }

        }
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }
    interface Listener{
        fun onClick(name: String)
    }
}
