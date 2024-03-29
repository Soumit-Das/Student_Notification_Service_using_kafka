package com.notificationservice.studentnotificationservice.repository;

import com.notificationservice.studentnotificationservice.models.Notifications;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<Notifications,Integer> {



}
