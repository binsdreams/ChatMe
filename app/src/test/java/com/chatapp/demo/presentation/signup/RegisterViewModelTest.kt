package com.chatapp.demo.presentation.signup

import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.chatapp.demo.R
import com.chatapp.demo.domain.AuthResponse
import com.chatapp.demo.domain.repo.AuthRepository
import com.chatapp.demo.util.isValidEmail
import com.chatapp.demo.util.isValidName
import com.chatapp.demo.util.isValidPassword
import com.chatapp.demo.util.isValidPhone
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull

@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest {

    @get:Rule
    val instantExecutorRule =
        InstantTaskExecutorRule() // This ensures that LiveData runs synchronously in tests.

    @Mock
    lateinit var repository: AuthRepository

    @Mock
    lateinit var resources: Resources

    private lateinit var registerViewModel: RegisterViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        registerViewModel = RegisterViewModel(repository)
        mockkStatic("com.chatapp.demo.util.ValidatorUtilKt") // Use the fully qualified file path for top-level functions
    }

    @Test
    fun `test callToRegister should update LiveData on success`() {
        // Arrange
        val name = "John Doe"
        val email = "john@example.com"
        val phone = "1234567890"
        val password = "ValidPassword123"
        val expectedResponse = AuthResponse(true, null)

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String?) -> Unit>(4)
            callback(true, null)  // Trigger the callback with the failure response
            null
        }.`when`(repository)
            .registerUser(anyOrNull(), anyOrNull(), anyOrNull(), anyOrNull(), anyOrNull())

        // Observe the LiveData
        val observer = mock(Observer::class.java) as Observer<AuthResponse>
        registerViewModel._RegisterResponsesLive.observeForever(observer)

        // Act
        registerViewModel.callToRegister(name, email, phone, password)

        // Assert
        verify(observer).onChanged(expectedResponse) // Check if LiveData was updated with the correct response
    }

    @Test
    fun `test validateAllFields with invalid email should update LiveData`() {
        // Arrange
        val name = "John Doe"
        val email = "invalid-email"
        val phone = "1234567890"
        val password = "ValidPassword123"

        // Mock the resources behavior
        `when`(resources.getString(R.string.error_email)).thenReturn("Invalid email format")

        // Call validateAllFields
        val result = registerViewModel.validateAllFields(name, email, phone, password, resources)

        // Assert that validation fails and LiveData is updated
        assertFalse(result)
        verify(repository, never()).registerUser(
            anyOrNull(),
            anyOrNull(),
            anyOrNull(),
            anyOrNull(),
            anyOrNull()
        ) // Ensure repository method is not called
    }

    @Test
    fun `test validateAllFields with valid inputs should return true`() {
        // Arrange
        val name = "John Doe"
        val email = "john@example.com"
        val phone = "1234567890"
        val password = "ValidPassword123"

        every { isValidEmail("john@example.com") } returns true
        every { isValidPassword("ValidPassword123") } returns true
        every { isValidName("John Doe") } returns true
        every { isValidPhone("1234567890") } returns true

        // Act
        val result = registerViewModel.validateAllFields(name, email, phone, password, resources)

        // Assert
        assertTrue(result) // Validation should pass with valid inputs
    }
}