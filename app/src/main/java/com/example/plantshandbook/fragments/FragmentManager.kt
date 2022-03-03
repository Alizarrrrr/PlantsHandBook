package com.example.plantshandbook.fragments

import androidx.appcompat.app.AppCompatActivity
import com.example.plantshandbook.R


object FragmentManager {
    var currentFrag: BaseFragment? = null

    fun setFragment(newFrag: BaseFragment, activity: AppCompatActivity) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.place_holder_main, newFrag)
        transaction.commit()
        currentFrag = newFrag
    }

}