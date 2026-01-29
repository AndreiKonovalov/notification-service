package ru.fintech.notification.dto

data class NotificationRequest(
    val userId: String,
    val message: String
)
