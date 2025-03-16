package com.chatapp.demo.util


// Regex for validating a name (allows letters, spaces, and hyphens)
private const val NAME_REGEX = "^[A-Za-z\\s-]{2,50}$"

// Regex for validating an email address
private const val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"

// Regex for validating a phone number (supports international formats)
private const val PHONE_REGEX = "^[+]?[0-9]{10,15}$"

// Regex for validating a password (at least 6 characters, 1 uppercase, 1 lowercase, 1 digit, 1 special character)
private const val PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{6,8}$"

/**
 * Validates a name.
 *
 * @param name The name to validate.
 * @return `true` if the name is valid, `false` otherwise.
 */
fun isValidName(name: String): Boolean {
    return name.matches(NAME_REGEX.toRegex())
}

/**
 * Validates an email address.
 *
 * @param email The email address to validate.
 * @return `true` if the email is valid, `false` otherwise.
 */
fun isValidEmail(email: String): Boolean {
    return email.matches(EMAIL_REGEX.toRegex())
}

/**
 * Validates a phone number.
 *
 * @param phone The phone number to validate.
 * @return `true` if the phone number is valid, `false` otherwise.
 */
fun isValidPhone(phone: String): Boolean {
    return phone.matches(PHONE_REGEX.toRegex())
}

/**
 * Validates a password.
 *
 * @param password The password to validate.
 * @return `true` if the password is valid, `false` otherwise.
 */
fun isValidPassword(password: String): Boolean {
    return password.matches(PASSWORD_REGEX.toRegex())
}

/**
 * Validates all fields (name, email, phone, password).
 *
 * @param name The name to validate.
 * @param email The email address to validate.
 * @param phone The phone number to validate.
 * @param password The password to validate.
 * @return `true` if all fields are valid, `false` otherwise.
 */
fun validateAllFields(name: String, email: String, phone: String, password: String): Boolean {
    return isValidName(name) && isValidEmail(email) && isValidPhone(phone) && isValidPassword(password)
}