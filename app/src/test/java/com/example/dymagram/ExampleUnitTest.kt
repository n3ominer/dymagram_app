package com.example.dymagram

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testFunctionName() {
        // Préparation
        val value1 = 3
        val value2 = 3
        val result = 7

        // Execution
        val sum = value1 + value2

        // Vérification
        assertEquals(result, sum)
    }

    @Test
    fun `function should test something`() {

        assertTrue(5 < 3)

    }
}