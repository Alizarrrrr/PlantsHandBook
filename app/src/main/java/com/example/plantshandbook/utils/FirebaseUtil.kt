package com.example.plantshandbook.utils

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.plantshandbook.entities.FirebaseItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking




class FirebaseUtil() {

    var plants = ArrayList<FirebaseItem>()
    val db = FirebaseFirestore.getInstance()
    //val storage = Firebase.storage

    val plantColName1 = "xVD6Q8LEwKJtHdPn5Pno"
    val plantColName2 = "HnNOPCswda5DG8M7PuxP"
    val plantColName3 = "wYsgajGdL4P60ATDisoe"
    val plantColName4 = "usObdR1rG5ElVnTu97aH"
    val plantColName5 = "OzKd7RUwUEdwVsqzzNs7"

    fun readDb() = runBlocking {
        launch{
            db.collection("plants")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val firebaseItem = document.toObject(FirebaseItem::class.java)
                    plants.add(firebaseItem)

                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

        }

    }
/*
    fun downloadFile(){
        readDb()
        val httpsReference = storage.getReferenceFromUrl(
        storage.child("users/me/profile.png").downloadUrl.addOnSuccessListener {
            // Got the download URL for 'users/me/profile.png'
        }.addOnFailureListener {
            // Handle any errors
        }

    } */










}