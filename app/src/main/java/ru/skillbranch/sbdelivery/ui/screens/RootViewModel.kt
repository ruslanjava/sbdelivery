package ru.skillbranch.sbdelivery.ui.screens

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import ru.skillbranch.sbdelivery.extensions.isNetworkAvailable
import ru.skillbranch.sbdelivery.repository.root.RootRepository
import ru.skillbranch.sbdelivery.viewModel.BaseViewModel
import ru.skillbranch.sbdelivery.viewModel.Event
import javax.inject.Inject

class RootViewModel : BaseViewModel() {

    @Inject
    lateinit var appContext: Context

    @Inject
    lateinit var repository: RootRepository

    @ExperimentalCoroutinesApi
    internal fun observeSyncResult(
        owner: LifecycleOwner, observer: Observer<LoadResult<Boolean>>
    ) {
        val liveData = MutableLiveData<Event<LoadResult<Boolean>>>()
        liveData.postValue(Event(LoadResult.Loading(false)))

        launchSafely {
            if (repository.isNeedUpdate()) {
                if (appContext.isNetworkAvailable()) {
                    repository.sync()
                    liveData.postValue(Event(LoadResult.Success(true)))
                } else {
                    liveData.postValue(Event(LoadResult.Error(
                        "Интернет недоступен, приложение может работать некорректно"
                    )))
                }
            } else {
                delay(3000)
                liveData.postValue(Event(LoadResult.Success(true)))
            }
        }

        liveData.observeEvents(owner, observer)
    }

}