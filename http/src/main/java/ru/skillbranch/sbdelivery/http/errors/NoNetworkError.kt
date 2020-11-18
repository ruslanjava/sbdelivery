package ru.skillbranch.sbdelivery.http.errors

import java.io.IOException

class NoNetworkError(
    override val message: String = "Network not available"
) : IOException(message)