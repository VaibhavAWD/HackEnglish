package com.vaibhavdhunde.app.hackenglish.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vaibhavdhunde.app.hackenglish.data.HackEnglishRepository
import com.vaibhavdhunde.app.hackenglish.ui.login.LoginViewModel
import com.vaibhavdhunde.app.hackenglish.ui.register.RegisterViewModel

class ViewModelFactory(
    private val repository: HackEnglishRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(repository)
                isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(repository)
                else -> throw IllegalArgumentException("Unknown model class: $modelClass")
            }
        } as T
    }
}