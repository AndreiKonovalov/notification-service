package ru.fintech.notification

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.kafka.annotation.EnableKafka

@SpringBootApplication
@EnableKafka
class NotificationApplication

fun main(args: Array<String>) {
    SpringApplication.run(NotificationApplication::class.java, *args)
    }
