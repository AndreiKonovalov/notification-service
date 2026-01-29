package ru.fintech.notification.controller;

@RestController
public class NotificationHealthController {

    @GetMapping("/health")
    public String health() {
        return "OK";
    }

}
