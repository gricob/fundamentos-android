package io.keepcoding.eh_ho.login

class SignInValidator {
    fun validate(signInForm: SignInForm): Boolean {
        var isValid = true

        if (signInForm.username.text.isBlank()) {
            isValid = false
            signInForm.username.error = "This field is required"
        }

        if (signInForm.password.text.isBlank()) {
            isValid = false
            signInForm.password.error = "This field is required"
        }

        return isValid
    }
}