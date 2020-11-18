package ru.skillbranch.sbdelivery.viewModel

class Event<out T>(val content: T) {

    var consumed = false

}