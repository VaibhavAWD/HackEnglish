package com.vaibhavdhunde.app.hackenglish.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vaibhavdhunde.app.hackenglish.data.HackEnglishRepository
import com.vaibhavdhunde.app.hackenglish.util.Event

class LoginViewModel(private val repository: HackEnglishRepository) : ViewModel() {

    private val _registerUserEvent = MutableLiveData<Event<Unit>>()
    val registerUserEvent: LiveData<Event<Unit>> = _registerUserEvent

    fun registerUser() {
        _registerUserEvent.value = Event(Unit)
    }
}