package com.example.plantshandbook.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

import java.io.ByteArrayOutputStream
import java.io.File

object Base64CoderDecoder {
    fun encoder(bm: Bitmap): String?{
            val baos = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val b = baos.toByteArray()
            return android.util.Base64.encodeToString(b, android.util.Base64.DEFAULT)
    }

    fun decoder (encodedString: String): Bitmap {
        val imageBytes = Base64.decode(encodedString, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        return decodedImage
    }
    fun encoderFile (img: File): String?{
        val bm = BitmapFactory.decodeFile(img.getAbsolutePath())
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return android.util.Base64.encodeToString(b, android.util.Base64.DEFAULT)
    }
}