package com.example.plantshandbook.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.plantshandbook.entities.FirebaseListItem
import com.example.plantshandbook.entities.ImageItem
import com.example.plantshandbook.entities.StatItem


@Database(entities = arrayOf(ImageItem::class, StatItem::class, FirebaseListItem::class), version = 1, exportSchema = false)


abstract class RoomSingleton : RoomDatabase() {
    abstract fun roomDAO(): Dao

    companion object {
        @Volatile
        private var instance: RoomSingleton? = null
        fun getInstance(context: Context): RoomSingleton {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context, RoomSingleton::class.java, "Sample.db").build()
                return instance as RoomSingleton
            }
            return instance as RoomSingleton
        }
    }

}
