package com.example.plantshandbook.db

import android.app.Application
import androidx.lifecycle.*
import com.example.plantshandbook.entities.FirebaseListItem
import com.example.plantshandbook.entities.ImageItem
import com.example.plantshandbook.entities.StatItem

import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    var roomSingleton: RoomSingleton
    val allImage: LiveData<List<ImageItem>>
    val allStat: LiveData<List<StatItem>>
    val allFbSave: LiveData<List<FirebaseListItem>>


    init {
        roomSingleton = RoomSingleton.getInstance(application)
        allImage = roomSingleton.roomDAO().getAllImage()
        allStat = roomSingleton.roomDAO().getAllStat()
        allFbSave = roomSingleton.roomDAO().getAllFb()
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

    suspend fun getAllFbList(): List<FirebaseListItem>{
        return roomSingleton.roomDAO().getAllFbList()
    }

    fun insertStat(item: StatItem) = viewModelScope.launch {
        roomSingleton.roomDAO().insertStat(item)
    }

    fun updateStat(item: StatItem) = viewModelScope.launch {
        roomSingleton.roomDAO().updateStat(item)
    }

    fun insertFb(item: FirebaseListItem) = viewModelScope.launch {
        roomSingleton.roomDAO().insertFb(item)
    }

    fun updateFb(item: FirebaseListItem) = viewModelScope.launch {
        roomSingleton.roomDAO().updateFb(item)
    }

    fun deleteFb(id: Int) = viewModelScope.launch {
        roomSingleton.roomDAO().deleteFb(id)
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