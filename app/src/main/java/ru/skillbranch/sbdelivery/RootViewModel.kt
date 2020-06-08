package ru.skillbranch.sbdelivery

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RootViewModel(val app: Application) : AndroidViewModel(app) {

    fun syncDataIfNeed() : LiveData<LoadResult<Boolean>> {
        val result : MutableLiveData<LoadResult<Boolean>> = MutableLiveData(LoadResult.Loading(false))
        viewModelScope.launch(Dispatchers.IO) {
            delay(3000)
            result.postValue(LoadResult.Success(true))
        }
        return result
    }

}