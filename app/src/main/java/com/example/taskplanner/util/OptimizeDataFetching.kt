package com.example.taskplanner.util

import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

inline fun <T> fetchData(call: () -> Resources<T>): Resources<T> {
    return try {
        call.invoke()
    } catch (e: Exception) {
        when (e) {
            is FirebaseAuthEmailException -> Resources.Error(MAIL_ERROR)
            is FirebaseAuthUserCollisionException -> Resources.Error(ALREADY_USED_MAIL_ERROR)
            is FirebaseAuthWeakPasswordException -> Resources.Error(WEAK_PASSWORD_ERROR)
            else -> Resources.Error(e.message!!)
        }
    }
}

inline fun <T> dataFetcher(
    call: () -> Resources<T>,
    errorAction: (error: String) -> Unit,
): T? {
    return when (val data = call.invoke()) {
        is Resources.Success -> data.data
        is Resources.Error -> {
            errorAction.invoke(data.message)
            null
        }
    }
}

const val MAIL_ERROR = "enter the correct mail"
const val ALREADY_USED_MAIL_ERROR = "mail is already used"
const val WEAK_PASSWORD_ERROR = "weak password"