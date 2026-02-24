package ru.fintech.notification.dto

import java.time.LocalDateTime

data class ClientRegisteredEventDto(
    val eventType: String,
    val clientId: Long,
    val email: String,
    val fullName: String,
    val registeredAt: LocalDateTime
)

