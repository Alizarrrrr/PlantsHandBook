package com.example.plantshandbook.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.example.plantshandbook.activities.MainActivity.Companion.enteredName
import com.example.plantshandbook.databinding.SaveImageDialogBinding

object SaveImagDialog {
    fun showDialog(context: Context, listener: Listener){
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = SaveImageDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.apply {
            bSave.setOnClickListener {

                    enteredName = edNameObjectPhoto.text.toString()
                if (enteredName != "") {
                    listener.onClick()
                    dialog?.dismiss()
                }else{
                    Toast.makeText(context, "Name empty", Toast.LENGTH_SHORT).show()

                }
            }
            bCancel.setOnClickListener {
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