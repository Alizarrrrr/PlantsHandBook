package com.example.plantshandbook.db

import android.app.Application
import androidx.lifecycle.*
import com.example.plantshandbook.entities.ImageItem
import com.example.plantshandbook.entities.StatItem
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    var roomSingleton: RoomSingleton
    val allImage: LiveData<List<ImageItem>>
    val allStat: LiveData<List<StatItem>>

    init {
        roomSingleton = RoomSingleton.getInstance(application)
        allImage = roomSingleton.roomDAO().getAllImage()
        allStat = roomSingleton.roomDAO().getAllStat()
    }

    fun insertImage(item: ImageItem) = viewModelScope.launch {
        roomSingleton.roomDAO().insertImage(item)
    }

    fun updateImage(item: ImageItem) = viewModelScope.launch {
        roomSingleton.roomDAO().updateImage(item)
    }

    fun deleteImage(id: Int) = viewModelScope.launch {
        roomSingleton.roomDAO().deleteImage(id)
    }

    suspend fun getAllImageList(): List<ImageItem> {
        return roomSingleton.roomDAO().getAllImageList()
    }

    suspend fun getAllStatList(): List<StatItem> {
        return roomSingleton.roomDAO().getAllStatList()
    }

    fun insertStat(item: StatItem) = viewModelScope.launch {
        roomSingleton.roomDAO().insertStat(item)
    }

    fun updateStat(item: StatItem) = viewModelScope.launch {
        roomSingleton.roomDAO().updateStat(item)
    }

}


/*
class  MainViewModelFactory(val database: MainDataBase) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MainViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return  MainViewModel(database) as T
            }
            throw IllegalAccessException("Unknown ViewModelClass")
        }
 */