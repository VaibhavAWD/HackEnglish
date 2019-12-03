package com.vaibhavdhunde.app.hackenglish.ui.register

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

class RegisterViewModel(private val repository: HackEnglishRepository) : ViewModel() {

    // Two-way DataBinding
    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _showMessage = MutableLiveData<Event<Any>>()
    val showMessage: LiveData<Event<Any>> = _showMessage

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean> = _showProgress

    private val _loginUserEvent = MutableLiveData<Event<Unit>>()
    val loginUserEvent: LiveData<Event<Unit>> = _loginUserEvent

    private val _userRegisteredEvent = MutableLiveData<Event<Unit>>()
    val userRegisteredEvent: LiveData<Event<Unit>> = _userRegisteredEvent

    private val _closeKeyboardEvent = MutableLiveData<Event<Unit>>()
    val closeKeyboardEvent: LiveData<Event<Unit>> = _closeKeyboardEvent

    fun registerUser() {
        if (!hasValidData()) return
        _closeKeyboardEvent.value = Event(Unit)
        viewModelScope.launch {
            showProgress()
            delay(2000) // remove this afterwards
            val result = repository.registerUser(name.value!!, email.value!!, password.value!!)
            hideProgress()
            if (result is Success) {
                // user registration successful
                UserInfo.saveUser(result.data)
                _userRegisteredEvent.value = Event(Unit)
            } else {
                // user registration failed
                val message = (result as Error).exception
                showMessage(message)
            }
        }
    }

    fun loginUser() {
        _loginUserEvent.value = Event(Unit)
    }

    private fun hasValidData(): Boolean {
        var isValid = false

        val cName = name.value
        val cEmail = email.value
        val cPassword = password.value

        if (cName.isNullOrEmpty()) {
            showMessage(R.string.error_empty_name)
        } else if (cEmail.isNullOrEmpty()) {
            showMessage(R.string.error_empty_email)
        } else if (!EmailValidator.isValidEmail(cEmail)) {
            showMessage(R.string.error_invalid_email)
        } else if (cPassword.isNullOrEmpty()) {
            showMessage(R.string.error_empty_password)
        } else if (cPassword.length < 6) {
            showMessage(R.string.error_invalid_password)
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