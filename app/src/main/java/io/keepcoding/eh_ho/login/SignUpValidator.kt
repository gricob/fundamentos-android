package io.keepcoding.eh_ho.login

class SignUpValidator {
    fun validate(signUpForm: SignUpForm): Boolean {
        var isValid = true

        if (signUpForm.username.text.isBlank()) {
            isValid = false
            signUpForm.username.error = "This field is required"
        }

        if (signUpForm.email.text.isBlank()) {
            isValid = false
            signUpForm.email.error = "This field is required"
        }

        if (signUpForm.password.text.isBlank()) {
            isValid = false
            signUpForm.password.error = "This field is required"
        }

        if (signUpForm.passwordConfirmation.text.isBlank()) {
            isValid = false
            signUpForm.passwordConfirmation.error = "This field is required"
        } else if (!signUpForm.password.text.equals(signUpForm.passwordConfirmation.text)) {
            isValid = false
            signUpForm.passwordConfirmation.error = "Password and confirmation mismatch"
        }

        return isValid
    }
}