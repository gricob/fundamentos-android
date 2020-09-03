package io.keepcoding.eh_ho.login

import android.widget.EditText

data class SignInForm (
    val username: EditText,
    val password: EditText
)

data class SignUpForm (
    val username: EditText,
    val email: EditText,
    val password: EditText,
    val passwordConfirmation: EditText
)