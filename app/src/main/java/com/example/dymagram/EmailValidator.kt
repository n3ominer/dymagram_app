package com.example.dymagram

class EmailValidator {

    fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.endsWith(".com") && email.length > 20
    }
}