package ru.fintech.notification.kafka.consumer

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class NotificationConsumer {

    private val log = LoggerFactory.getLogger(javaClass)

    @KafkaListener(
        topics = ["notification-events"]
    )

    fun listen(message: String) {
        println(">>> RECEIVED: $message")
    }
}
