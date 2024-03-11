package com.imasha.kotlindemo.unit_test


import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest {
    @Test
    fun `empty username returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `Username already exist return true`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "John",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `Password not match return false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "John",
            "1234",
            "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `Valid all inputs return true`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Smith",
            "123",
            "123"
        )
        assertThat(result).isTrue()
    }
}