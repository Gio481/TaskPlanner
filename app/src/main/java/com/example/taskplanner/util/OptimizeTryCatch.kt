package com.example.taskplanner.util

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

inline fun <T> fetchData(call: () -> Resources<T>): Resources<T> {
    return try {
        call.invoke()
    } catch (e: Exception) {
        when (e) {
            is FirebaseAuthInvalidCredentialsException -> Resources.Error(MAIL_ERROR)
            is FirebaseAuthUserCollisionException -> Resources.Error(ALREADY_USED_MAIL_ERROR)
            is FirebaseAuthWeakPasswordException -> Resources.Error(WEAK_PASSWORD_ERROR)
            else -> Resources.Error(e.message!!)
        }
    }
}

const val MAIL_ERROR = "enter the correct mail"
const val ALREADY_USED_MAIL_ERROR = "mail is already used"
const val WEAK_PASSWORD_ERROR = "weak password"