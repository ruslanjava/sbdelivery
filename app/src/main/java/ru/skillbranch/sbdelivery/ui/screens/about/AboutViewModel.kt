package ru.skillbranch.sbdelivery.ui.screens.about

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.skillbranch.sbdelivery.BuildConfig
import ru.skillbranch.sbdelivery.viewModel.BaseViewModel
import ru.skillbranch.sbdelivery.viewModel.Event

class AboutViewModel : BaseViewModel() {

    fun observeVersion(owner: LifecycleOwner, observer: Observer<String>) {
        val aboutVersion = MutableLiveData<String>()
        launchSafely {
            aboutVersion.postValue(BuildConfig.VERSION_NAME)
        }
        aboutVersion.observe(owner, observer)
    }

}