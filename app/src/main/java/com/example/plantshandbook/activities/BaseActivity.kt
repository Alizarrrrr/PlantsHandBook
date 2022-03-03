package com.example.plantshandbook.activities

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.example.plantshandbook.R
import com.gmail.samehadar.iosdialog.IOSDialog

open class BaseActivity: AppCompatActivity() {
    private var pg: IOSDialog? = null
    var metrics = DisplayMetrics()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        windowManager.defaultDisplay.getMetrics(metrics)
    }

    fun startProgress() {
        try {
            runOnUiThread {
                if (pg == null) {
                    pg = IOSDialog.Builder(this)
                        .setDimAmount(3f)
                        .setSpinnerColorRes(R.color.grey)
                        .setMessageColorRes(R.color.colorAccent)
                        .setCancelable(false)
                        .setMessageContentGravity(Gravity.END)
                        .build()

                    pg!!.show()
                }
            }
        } catch (e: Exception) {
            //e.printStackTrace();
        }
    }

    fun stopProgress() {
        try {
            runOnUiThread {
                if (pg != null) pg!!.dismiss()
            }
            pg = null
        } catch (e: Exception) {
        }
    }
}