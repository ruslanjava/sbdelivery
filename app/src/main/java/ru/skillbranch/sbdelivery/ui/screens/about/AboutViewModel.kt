package ru.skillbranch.sbdelivery.ui.screens.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.skillbranch.sbdelivery.BuildConfig

class AboutViewModel : ViewModel() {

    fun aboutVersion() : LiveData<String> {
        val aboutVersion = MutableLiveData<String>()
        GlobalScope.launch(Dispatchers.IO) {
            aboutVersion.postValue(BuildConfig.VERSION_NAME)
        }
        return aboutVersion
    }

}