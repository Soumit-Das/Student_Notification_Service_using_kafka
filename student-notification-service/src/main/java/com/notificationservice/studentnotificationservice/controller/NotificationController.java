package com.notificationservice.studentnotificationservice.controller;

import com.notificationservice.studentnotificationservice.models.Notifications;
import com.notificationservice.studentnotificationservice.models.Students;
import com.notificationservice.studentnotificationservice.repository.NotificationRepository;
import com.notificationservice.studentnotificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/sendNotification")
    public ResponseEntity<Notifications> createNotification(@RequestBody Students students){

        Notifications notifications = notificationService.createNotification(students);

        return ResponseEntity.status(HttpStatus.CREATED).body(notifications);

    }

//    @GetMapping("/getAllNotifications")
//    public ResponseEntity<List<Notifications>> getAllNotifications(){
//
//        List<Notifications> notifications = notificationService.getAllNotifications();
//
//        return new ResponseEntity<>(notifications,HttpStatus.OK);
//    }


    @GetMapping("/getAllNotificationsPagewise")
    public Page<Notifications> getAllNotificationsPagewise(@RequestParam int pageNumber,@RequestParam int numberOfData){

        return notificationService.getAllNotificationsPagewise(pageNumber,numberOfData);

    }

    @GetMapping("/getAllNotificationsPagewiseSorted")
    public Page<Notifications> getAllNotificationsPagewiseSorted(@RequestParam int pageNumber,@RequestParam int numberOfData,@RequestParam String sortingField,@RequestParam String sortingDirection){

        return notificationService.getAllNotificationsPagewiseSorted(pageNumber,numberOfData,sortingField,sortingDirection);

    }

}
