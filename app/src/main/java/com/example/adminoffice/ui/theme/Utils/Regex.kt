package com.example.adminoffice.ui.theme.Utils

fun isValidName(serviceName: String): Boolean {
    val nameRegex = Regex("^[a-zA-Z ]+\$")
    return nameRegex.matches(serviceName)
}

fun isValidDescription(serviceName: String): Boolean {
    val nameRegex = Regex("^[a-zA-Z 0-9 .]{31,}+\$")
    return nameRegex.matches(serviceName)
}
fun isValidPhoneNumber(phoneNumber: String): Boolean {
    val phoneRegex = Regex("\\d{4}\\d{7}")
    return phoneRegex.matches(phoneNumber)
}