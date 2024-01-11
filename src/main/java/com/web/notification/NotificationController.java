package com.web.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    // https://dkswnkk.tistory.com/702
    private final NotificationService notificationService;


    @GetMapping(value = "/subscribe/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable Long id) {
        return notificationService.subscribe(id);
    }

    @PostMapping("/send-data/{id}")
    public void sendData(@PathVariable Long id , HttpServletRequest req) {
        String data = req.getParameter("data") != null ? req.getParameter("data") : "data is null..";
        notificationService.notify(id, data);
    }
}