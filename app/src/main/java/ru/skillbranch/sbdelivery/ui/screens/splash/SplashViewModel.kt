package ru.skillbranch.sbdelivery.ui.screens.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class SplashViewModel : ViewModel() {

    var items: MutableLiveData<Boolean>? = null

    fun getObservableGallery(): LiveData<Boolean> {
        if (items == null) {
            items = MutableLiveData()
            viewModelScope.launch(Dispatchers.IO) {
                TimeUnit.SECONDS.sleep(3)
                items?.postValue(true)
            }
        }
        return items!!
    }

}
