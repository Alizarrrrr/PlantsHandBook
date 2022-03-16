package com.example.plantshandbook.activities

import android.app.Application
import com.example.plantshandbook.db.MainDataBase

class MainApp : Application() {
    val database by lazy { MainDataBase.getDataBase(this) }
}