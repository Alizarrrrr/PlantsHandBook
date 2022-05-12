package com.example.plantshandbook.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import  androidx.room.RoomDatabase
import com.example.plantshandbook.entities.ImageItem
import com.example.plantshandbook.entities.StatItem


@Database (entities = [ImageItem::class, StatItem::class], version = 1)
abstract class MainDataBase : RoomDatabase() {
    abstract fun getDao(): Dao

    companion object{
        @Volatile
        private var INSTANCE: MainDataBase? = null
        fun getDataBase(context: Context): MainDataBase{
            return  INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDataBase::class.java,
                    "plantshandbook.db"
                ).build()
                instance
            }
        }
    }

}