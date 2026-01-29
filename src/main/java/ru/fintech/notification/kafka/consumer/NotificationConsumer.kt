package ru.fintech.notification.kafka.consumer

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import ru.fintech.notification.dto.NotificationEvent

@Component
class NotificationConsumer {

    private val log = LoggerFactory.getLogger(javaClass)

    @KafkaListener(
        topics = ["notifications"],
        groupId = "notification-group"
    )
    fun listen(event: NotificationEvent) {
        log.info("Received notification from Kafka: {}", event)
    }
}
