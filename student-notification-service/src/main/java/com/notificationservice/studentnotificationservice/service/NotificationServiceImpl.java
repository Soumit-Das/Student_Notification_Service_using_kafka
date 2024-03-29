package com.notificationservice.studentnotificationservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notificationservice.studentnotificationservice.config.AppConstants;
import com.notificationservice.studentnotificationservice.models.Notifications;
import com.notificationservice.studentnotificationservice.models.Students;
import com.notificationservice.studentnotificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    public NotificationRepository notificationRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    KafkaProducer kafkaProducer;

    @KafkaListener(topics = AppConstants.STUDENT_TOPIC_NAME, groupId = "student_group")
    public void listenStudentData(String student) {

        System.out.println(student);
       try
       {
           Students student1 = objectMapper.readValue(student, new TypeReference<Students>() {});
           Notifications notifications =  createNotification(student1);
           String jsonNotifications = objectMapper.writeValueAsString(notifications);
           kafkaTemplate.send(AppConstants.NOTIFICATION_TOPIC_NAME,jsonNotifications);
       }
       catch (IOException e)
       {
           e.printStackTrace();
       }

    }

    @Override
    public Notifications createNotification(Students students) {

        Notifications notifications = new Notifications();
        notifications.setNotificationId(8);
        notifications.setNotificationType("adding student");
        notifications.setPayload(copy(students));
        notifications.setCreatedOn(new Date());

        return notificationRepository.save(notifications);

    }

    public Students copy(Students student)
    {
        Students st = new Students();
        st.setName(student.getName());
        st.setAddress(student.getAddress());
        st.setAge(student.getAge());
        st.setBatch(student.getBatch());
        st.setCreatedAt(new Date());
        return st;
    }



//    @Override
//    public List<Notifications> getAllNotifications(){
//
//        List<Notifications> notifications = notificationRepository.findAll();
//        kafkaProducer.sendNotify(notifications);
//
//        return notifications;
//    }


    @Override
    public Page<Notifications> getAllNotificationsPagewise(int pageNumber, int numberOfData) {

        PageRequest pageRequest = PageRequest.of(pageNumber-1,numberOfData);
        return notificationRepository.findAll(pageRequest);

    }

    @Override
    public Page<Notifications> getAllNotificationsPagewiseSorted(int pageNumber,int numberOfData,String sortingField,String sortingDirection){


        Sort.Direction direction = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(sortingDirection)) {
            direction = Sort.Direction.DESC;
        } else if (!"asc".equalsIgnoreCase(sortingDirection)) {
            throw new IllegalArgumentException("Invalid sorting direction. Must be 'asc' or 'desc'");
        }

        PageRequest pageRequest = PageRequest.of(pageNumber-1,numberOfData,direction,sortingField);

        return notificationRepository.findAll(pageRequest);

    }


}
