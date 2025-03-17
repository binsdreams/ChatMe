package com.chatapp.demo.presentation.login

import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.chatapp.demo.domain.AuthResponse
import com.chatapp.demo.domain.repo.AuthRepository
import com.chatapp.demo.util.isValidEmail
import com.chatapp.demo.util.isValidPassword
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.anyOrNull


@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule =
        InstantTaskExecutorRule() // Ensures LiveData updates synchronously

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()  // Initializes mocks

    @Mock
    lateinit var repository: AuthRepository

    @Mock
    lateinit var resources: Resources

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setup() {
        // Initialize ViewModel with mocked repository
        loginViewModel = LoginViewModel(repository)
        mockkStatic("com.chatapp.demo.util.ValidatorUtilKt") // Use the fully qualified file path for top-level functions
    }

    @Test
    fun `test callLogin with success response`() {
        // Mock the repository behavior
        val email = "test@example.com"
        val password = "password123"
        val expectedResponse = AuthResponse(true, null)
        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String?) -> Unit>(2)
            callback(true, null)  // Trigger the callback with the failure response
            null
        }.`when`(repository).loginUser(anyOrNull(), anyOrNull(), anyOrNull())

        // Observe the LiveData
        val observer = mock(Observer::class.java) as Observer<AuthResponse>
        loginViewModel._loginResponsesLive.observeForever(observer)

        // Call the method
        loginViewModel.callLogin(email, password)

        // Verify that the LiveData was updated correctly
        verify(observer).onChanged(expectedResponse)
    }

    @Test
    fun `test callLogin with failure response`() {
        // Mock the repository behavior
        val email = "test@example.com"
        val password = "wrongpassword"
        val expectedResponse = AuthResponse(false, "Invalid credentials")
        // Capture the callback passed into the loginUser method
        // Create an ArgumentCaptor for capturing the callback
        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String?) -> Unit>(2)
            callback(
                false,
                "Invalid credentials"
            )  // Trigger the callback with the failure response
            null
        }.`when`(repository).loginUser(anyOrNull(), anyOrNull(), anyOrNull())

        val observer = mock(Observer::class.java) as Observer<AuthResponse>
        loginViewModel._loginResponsesLive.observeForever(observer)

        // Call the method to perform login
        loginViewModel.callLogin(email, password)

        // Verify that the LiveData was updated correctly
        verify(observer).onChanged(expectedResponse)

        // Additional assert to check if the correct response was sent
        assertEquals(expectedResponse, loginViewModel._loginResponsesLive.value)
    }

    @Test
    fun `test validateAllFields with valid email and password`() {
        // Simulate the resources mock behavior
        every { isValidEmail("test@example.com") } returns true
        every { isValidPassword("password123") } returns true

        val email = "test@example.com"
        val password = "password123"

        // Call validateAllFields
        val result = loginViewModel.validateAllFields(email, password, resources)

        // Assert that validation passes (returns true)
        assertTrue(result)
    }

    @Test
    fun `test validateAllFields with invalid email`() {
        // Simulate the resources mock behavior
        `when`(resources.getString(anyInt())).thenReturn("Invalid email")
        `when`(resources.getString(anyInt())).thenReturn("Invalid password")

        val email = "invalidemail"
        val password = "password123"

        // Call validateAllFields
        val result = loginViewModel.validateAllFields(email, password, resources)

        // Assert that validation fails (returns false)
        assertFalse(result)
    }

    @Test
    fun `test validateAllFields with invalid password`() {
        // Simulate the resources mock behavior
        `when`(resources.getString(anyInt())).thenReturn("Invalid email")
        `when`(resources.getString(anyInt())).thenReturn("Invalid password")

        val email = "test@example.com"
        val password = "123" // Invalid password

        // Call validateAllFields
        val result = loginViewModel.validateAllFields(email, password, resources)

        // Assert that validation fails (returns false)
        assertFalse(result)
    }
}