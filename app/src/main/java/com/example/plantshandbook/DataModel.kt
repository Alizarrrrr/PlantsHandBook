package com.example.plantshandbook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataModel: ViewModel() {
    val indicatorbtnAddImg: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
}