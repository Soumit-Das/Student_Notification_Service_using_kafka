package com.notificationservice.studentnotificationservice.service;

import com.notificationservice.studentnotificationservice.config.AppConstants;
import com.notificationservice.studentnotificationservice.models.Notifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaProducer {

    @Autowired

    KafkaTemplate<String , String> kafkaTemplate;

    public void sendNotify(List<Notifications> notificationsList){

        Message<List<Notifications>> message = MessageBuilder.withPayload(notificationsList).setHeader(KafkaHeaders.TOPIC, AppConstants.NOTIFICATION_TOPIC_NAME).build();
        kafkaTemplate.send(message);

    }
}

