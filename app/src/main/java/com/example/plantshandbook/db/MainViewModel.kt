package com.example.plantshandbook.db

import androidx.lifecycle.*
import com.example.plantshandbook.entities.ImageItem
import kotlinx.coroutines.launch

class MainViewModel(dataBase: MainDataBase): ViewModel() {
    val dao = dataBase.getDao()
    val allImage: LiveData<List<ImageItem>> = dao.getAllImage().asLiveData()


    fun insertImage(item: ImageItem) = viewModelScope.launch {
        dao.insertImage(item)
    }




    fun updateImage(item: ImageItem) = viewModelScope.launch {
        dao.updateImage(item)
    }

    fun deleteImage(id: Int) = viewModelScope.launch {
        dao.deleteImage(id)
    }






    class  MainViewModelFactory(val database: MainDataBase) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MainViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return  MainViewModel(database) as T
            }
            throw IllegalAccessException("Unknown ViewModelClass")
        }

    }













}