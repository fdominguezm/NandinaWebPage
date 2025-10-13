package com.nandina.api.controllers;


import com.nandina.api.dtos.NotificationDto;
import com.nandina.api.forms.PageForm;
import com.nandina.api.models.PagedContent;
import com.nandina.api.models.User;
import com.nandina.api.models.notifications.Notification;
import com.nandina.api.services.implementations.NotificationPushService;
import com.nandina.api.services.interfaces.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;


@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationPushService notificationPushService;
    private final NotificationService notificationService;

    @GetMapping("/subscribe")
    public SseEmitter subscribe() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return notificationPushService.subscribe(user.getId());
    }

    @GetMapping
    public ResponseEntity<PagedContent<NotificationDto>> getNotifications(@Valid @ModelAttribute PageForm pageForm) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PagedContent<Notification> notifications = notificationService.getUnreadNotifications(user, pageForm.getPageOrDefault(), pageForm.getSizeOrDefault());
        List<NotificationDto> notificationDTOs = notifications.getContent().stream().map(NotificationDto::from).toList();
        PagedContent<NotificationDto> toReturn = new PagedContent<>(notificationDTOs, notifications.getCurrentPage(), notifications.getPageSize(), notifications.getTotalCount());
        return ResponseEntity.ok(toReturn);
    }

    @PostMapping("/read/{notificationId}")
    public ResponseEntity<?> readNotification(@PathVariable Long notificationId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        notificationService.readNotification(user, notificationId);
        return ResponseEntity.ok().build();
    }

}

