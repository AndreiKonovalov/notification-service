package ru.fintech.notification.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.fintech.notification.dto.NotificationRequest

@Service
class NotificationService {

    private val log = LoggerFactory.getLogger(javaClass)

    fun process(request: NotificationRequest) {
        log.info(
            "Processing notification: userId={}, message={}",
            request.userId,
            request.message
        )
    }
}
