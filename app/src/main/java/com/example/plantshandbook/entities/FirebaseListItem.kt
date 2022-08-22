package com.example.plantshandbook.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity (tableName = "firebase_list")
data class FirebaseListItem (
    @PrimaryKey (autoGenerate = true)
    val id: Int? ,
    @ColumnInfo (name = "name")
    val name: String ,
    @ColumnInfo (name = "token")
    val token: String,
    @ColumnInfo (name = "download_flag")
    val download_flag: Boolean
): Serializable