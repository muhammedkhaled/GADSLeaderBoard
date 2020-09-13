package com.khaled.gadsleaderboard.utils

import android.util.Patterns
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

class Validation {
    companion object {
        private fun isEmptyEditText(editText: EditText): Boolean {
            return editText.text.isNullOrEmpty()
        }

        fun isValidEmail(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isValidUserName(username: String): Boolean {
            return username.length >= 2
        }

        fun isValidPassword(password: String): Boolean {
            return password.length >= 6
        }

        fun validateInput(
            textInputLayout: TextInputLayout,
            emptyInputErrorMsg: String,
            invalidInputErrorMsg: String,
            inputPatternValidation: Boolean
        ): Boolean {
            return if (isEmptyEditText(textInputLayout.editText!!)) {
                textInputLayout.error = emptyInputErrorMsg
                false
            } else {
                if (inputPatternValidation) {
                    textInputLayout.isErrorEnabled = false
                    true
                } else {
                    textInputLayout.error = invalidInputErrorMsg
                    false
                }
            }
        }
    }
}