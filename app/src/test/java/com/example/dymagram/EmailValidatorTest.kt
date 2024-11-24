package com.example.dymagram

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test


class EmailValidatorTest {

    private val emailValidator = EmailValidator()

    @Test
    fun `validation function should return true`() {
        // Préparation
        val email = "totoaoeiazioeu@exemple.com"

        // Execution
        val result = emailValidator.isValidEmail(email)

        // Verification
        assertTrue(result)
    }

    @Test
    fun `validation function should return false`() {
        // Préparation
        val email = "toto@exemple.co"

        // Execution
        val result = emailValidator.isValidEmail(email)

        // Verification
        assertFalse(result)
    }

}