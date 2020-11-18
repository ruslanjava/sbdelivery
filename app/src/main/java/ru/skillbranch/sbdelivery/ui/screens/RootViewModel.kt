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
import javax.inject.Inject

class RootViewModel(val app: Application) : AndroidViewModel(app) {

    @Inject
    lateinit var repository: RootRepository

    @ExperimentalCoroutinesApi
    internal fun syncDataIfNeed() : LiveData<LoadResult<Boolean>> {
        val result : MutableLiveData<LoadResult<Boolean>> = MutableLiveData(
            LoadResult.Loading(false)
        )

        viewModelScope.launch(Dispatchers.IO) {
            if (repository.isNeedUpdate()) {
                if (app.applicationContext.isNetworkAvailable()) {
                    repository.sync()
                    result.postValue(LoadResult.Success(true))
                } else {
                    result.postValue(LoadResult.Error<Boolean>(
                        "Интернет недоступен, приложение может работать некорректно"
                    ))
                }
            } else {
                delay(3000)
                result.postValue(LoadResult.Success(true))
            }
        }
        return result
    }

}