package ru.skillbranch.sbdelivery.viewModel

import androidx.lifecycle.Observer

class EventObserver<T>(private val observer: Observer<T>) : Observer<Event<T>> {

    override fun onChanged(event: Event<T>) {
        if (!event.consumed) {
            event.consumed = true
            observer.onChanged(event.content)
        }
    }

}
