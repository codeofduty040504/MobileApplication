package com.example.adminoffice.ui.theme.Utils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

fun convertDateFormat(inputDate: String): String {
    try {
        // Input date format
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        // Output date format
        val outputFormat = SimpleDateFormat("MM-dd-yyyy")

        // Parse input date string and format it
        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date)
    } catch (e: Exception) {
        // Handle parsing errors
        return "Invalid Date"
    }
}