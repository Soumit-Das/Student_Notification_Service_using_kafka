package com.notificationservice.studentnotificationservice.config;

import com.notificationservice.studentnotificationservice.models.Students;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {


    public NewTopic topic(){
        return TopicBuilder.name(AppConstants.STUDENT_TOPIC_NAME).build();

    }


}
