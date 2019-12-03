package com.vaibhavdhunde.app.hackenglish.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhavdhunde.app.hackenglish.R
import com.vaibhavdhunde.app.hackenglish.data.HackEnglishRepository
import com.vaibhavdhunde.app.hackenglish.data.Result.Error
import com.vaibhavdhunde.app.hackenglish.data.Result.Success
import com.vaibhavdhunde.app.hackenglish.data.UserInfo
import com.vaibhavdhunde.app.hackenglish.util.EmailValidator
import com.vaibhavdhunde.app.hackenglish.util.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: HackEnglishRepository) : ViewModel() {

    // Two-way DataBinding
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _showMessage = MutableLiveData<Event<Any>>()
    val showMessage: LiveData<Event<Any>> = _showMessage

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean> = _showProgress

    private val _registerUserEvent = MutableLiveData<Event<Unit>>()
    val registerUserEvent: LiveData<Event<Unit>> = _registerUserEvent

    private val _userLoginEvent = MutableLiveData<Event<Unit>>()
    val userLoginEvent: LiveData<Event<Unit>> = _userLoginEvent

    private val _closeKeyboardEvent = MutableLiveData<Event<Unit>>()
    val closeKeyboardEvent: LiveData<Event<Unit>> = _closeKeyboardEvent

    fun loginUser() {
        if (!hasValidData()) return
        _closeKeyboardEvent.value = Event(Unit)
        viewModelScope.launch {
            showProgress()
            delay(2000) // remove this afterwards
            val result = repository.loginUser(email.value!!, password.value!!)
            hideProgress()
            if (result is Success) {
                // authentication successful
                UserInfo.saveUser(result.data)
                _userLoginEvent.value = Event(Unit)
            } else {
                // authentication failed
                val message = (result as Error).exception
                showMessage(message)
            }
        }
    }

    fun registerUser() {
        _registerUserEvent.value = Event(Unit)
    }

    private fun hasValidData(): Boolean {
        var isValid = false

        val cEmail = email.value
        val cPassword = password.value

        if (cEmail.isNullOrEmpty()) {
            showMessage(R.string.error_empty_email)
        } else if (!EmailValidator.isValidEmail(cEmail)) {
            showMessage(R.string.error_invalid_email)
        } else if (cPassword.isNullOrEmpty()) {
            showMessage(R.string.error_empty_password)
        } else {
            isValid = true
        }

        return isValid
    }

    private fun showProgress() {
        _showProgress.value = true
    }

    private fun hideProgress() {
        _showProgress.value = false
    }

    private fun showMessage(message: Any) {
        if (message is Int || message is String) {
            _showMessage.value = Event(message)
        } else {
            throw RuntimeException("Message should be either of type Int or String only.")
        }
    }
}