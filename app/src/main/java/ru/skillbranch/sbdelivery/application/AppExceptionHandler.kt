package ru.skillbranch.sbdelivery.application

import timber.log.Timber

/**
 * Добавляет важную функциональность - запись в файл неперехваченных исключений.
 */
class AppExceptionHandler
internal constructor(
        private val defaultHandler: Thread.UncaughtExceptionHandler?
) : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(thread: Thread, error: Throwable) {
        Timber.e(error, "thread: %s", thread)
        defaultHandler?.uncaughtException(thread, error)
    }

}