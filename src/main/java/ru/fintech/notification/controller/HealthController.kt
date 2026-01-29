package ru.fintech.notification.controller

import org.springframework.web.bind.annotation.*
import ru.fintech.notification.dto.NotificationRequest
import ru.fintech.notification.service.NotificationService

@RestController
@RequestMapping("/api/notifications")
class HealthController(
    private val notificationService: NotificationService
) {

    @PostMapping
    fun send(@RequestBody request: NotificationRequest) {
        notificationService.process(request)
    }

    @GetMapping("/health")
    fun health(): String = "OK"

}