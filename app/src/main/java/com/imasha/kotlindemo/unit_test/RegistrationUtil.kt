package com.imasha.kotlindemo.unit_test

object RegistrationUtil {

    private val users = listOf("John", "Peter")

    fun validateRegistrationInput(
        userName: String,
        password: String,
        confirmedPassword: String
    ): Boolean {
        if(userName.isEmpty() || password.isEmpty()) {
            return false;
        }
        if(userName in users) {
            return false;
        }
        if(password != confirmedPassword) {
            return false;
        }
        return true;
    }
}