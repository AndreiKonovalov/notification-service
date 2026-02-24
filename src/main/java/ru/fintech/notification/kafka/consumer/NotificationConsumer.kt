package ru.fintech.notification.kafka.consumer

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import ru.fintech.notification.dto.ClientRegisteredEventDto

@Component
class NotificationConsumer(
    private val objectMapper: ObjectMapper
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["analytics-events"])
    fun listen(record: ConsumerRecord<String, JsonNode>) {

        val payload = record.value()

        if (payload == null) {
            log.warn(
                "Received tombstone message, key={}, partition={}, offset={}",
                record.key(),
                record.partition(),
                record.offset()
            )
            return
        }
        val eventType = payload["eventType"]?.asText()

        log.info("Received eventType={}, key={}", eventType, record.key())

        when (eventType) {
            "CLIENT_REGISTERED" -> {
                val event = objectMapper.treeToValue(
                    payload,
                    ClientRegisteredEventDto::class.java
                )
                handleClientRegistered(event)
            }

            "TRANSFER_COMPLETED" -> {
                handleTransferCompleted(payload)
            }

            else -> log.warn("Unknown event type: {}", eventType)
        }
    }

    private fun handleClientRegistered(event: ClientRegisteredEventDto) {
        log.info(
            "Notify: new client registered id={}, email={}, name={}",
            event.clientId,
            event.email,
            event.fullName
        )

    }

    private fun handleTransferCompleted(payload: JsonNode) {
        log.info("Transfer completed event: {}", payload)
    }

}
