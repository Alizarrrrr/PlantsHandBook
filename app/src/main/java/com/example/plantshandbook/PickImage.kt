package com.example.plantshandbook

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class PickImage(val context: Context, val mFragment: Fragment, val btn: Button, val imgview:ImageView) {
    init {
        btnListener()
    }

    fun btnListener() {
        btn.setOnClickListener {
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    mFragment.requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    //permission already granted
                    pickImageFromGallery();
                }
            } else {
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }
    }



    fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        mFragment.startActivityForResult(intent, IMAGE_PICK_CODE)
        IMAGE_PICK_CODE_TRANSMIT = 1003
    }

    fun createImView(){
        if (IMAGE_PICK_CODE == 1003){
            imgview.setImageURI(data?.data)
        }
    }




    companion object {
        //image pick code
        val IMAGE_PICK_CODE = 1000;
        var IMAGE_PICK_CODE_TRANSMIT = 1002;
        //Permission code
        val PERMISSION_CODE = 1001;
    }



}