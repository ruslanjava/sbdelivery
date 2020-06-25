package ru.skillbranch.sbdelivery.ui.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.skillbranch.sbdelivery.extensions.isNetworkAvailable
import ru.skillbranch.sbdelivery.repository.root.RootRepository

class RootViewModel(val app: Application) : AndroidViewModel(app) {

    private val repository = RootRepository

    @ExperimentalCoroutinesApi
    fun syncDataIfNeed() : LiveData<LoadResult<Boolean>> {
        val result : MutableLiveData<LoadResult<Boolean>> = MutableLiveData(
            LoadResult.Loading(false)
        )

        viewModelScope.launch(Dispatchers.IO) {
            if (repository.isNeedUpdate()) {
                if (!app.applicationContext.isNetworkAvailable()) {
                    result.postValue(LoadResult.Error<Boolean>(
                            "Интернет недоступен, приложение может работать некорректно"
                    ))
                    return@launch
                }
                repository.sync()
                result.postValue(LoadResult.Success(true))
            } else {
                delay(3000)
                result.postValue(LoadResult.Success(true))
            }
        }
        return result
    }

}