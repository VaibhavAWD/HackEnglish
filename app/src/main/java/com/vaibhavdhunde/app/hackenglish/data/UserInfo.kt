package com.vaibhavdhunde.app.hackenglish.data

import com.vaibhavdhunde.app.hackenglish.model.User
import io.paperdb.Paper

object UserInfo {

    private const val USER = "user"

    fun saveUser(user: User) {
        Paper.book().write(USER, user)
    }

    fun getUser(): User? {
        return Paper.book().read<User>(USER)
    }

    fun deleteUser() {
        Paper.book().destroy()
    }

    fun getApiKey(): String {
        val user = Paper.book().read<User>(USER)
        return user.api_key
    }

    fun isLoggedIn(): Boolean {
        return getUser() != null
    }
}