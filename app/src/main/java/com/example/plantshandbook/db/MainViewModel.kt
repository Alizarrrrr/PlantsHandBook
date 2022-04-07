package com.example.plantshandbook.db

import android.app.Application
import androidx.lifecycle.*
import com.example.plantshandbook.entities.ImageItem
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    var roomSingleton: RoomSingleton
    val allImage: LiveData<List<ImageItem>>

    init {
        roomSingleton = RoomSingleton.getInstance(application)
        allImage = roomSingleton.roomDAO().getAllImage()
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