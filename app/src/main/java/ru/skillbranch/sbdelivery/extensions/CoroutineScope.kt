package ru.skillbranch.sbdelivery.extensions

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

fun CoroutineScope.launchSafely(
        context: CoroutineContext,
        errors: MutableLiveData<Throwable>,
        block: suspend CoroutineScope.() -> Unit
): Job {
    val handler = CoroutineExceptionHandler { _, error ->
        if (error !is CancellationException) { errors.postValue(error) }
    }
    return launch(context + handler, CoroutineStart.DEFAULT, block)
}