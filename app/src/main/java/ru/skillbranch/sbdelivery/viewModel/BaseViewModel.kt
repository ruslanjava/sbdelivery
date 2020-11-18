package ru.skillbranch.sbdelivery.viewModel

import androidx.lifecycle.*
import kotlinx.coroutines.*

open class BaseViewModel : ViewModel() {

    protected val errors = MutableLiveData<Event<Throwable>>()

    fun observeErrors(owner: LifecycleOwner, observer: Observer<Throwable>) {
        errors.observeEvents(owner, observer)
    }

    fun <T> MutableLiveData<Event<T>>.observeEvents(owner: LifecycleOwner, observer: Observer<T>) {
        observe(owner, EventObserver(observer))
    }

    fun launchSafely(block: suspend CoroutineScope.() -> Unit): Job {
        val handler = CoroutineExceptionHandler { _, error ->
            if (error !is CancellationException) {
                errors.postValue(Event(error))
            }
        }
        return GlobalScope.launch(Dispatchers.IO + handler, CoroutineStart.DEFAULT, block)
    }

}
