package com.sms.Student_Management_System.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sms.Student_Management_System.AppConstants;
import com.sms.Student_Management_System.Model.Notifications;
import com.sms.Student_Management_System.Model.NotificationsPagewise;
import com.sms.Student_Management_System.Model.Students;
import com.sms.Student_Management_System.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StudentsServiceImpl implements StudentService{

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    public StudentRepository studentRepository;

    @Autowired
    KafkaTemplate<String,Students> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Students updateStudentById(int id, Students student) {

        Optional<Students> existingStudent = studentRepository.findById(id);


        Students st = existingStudent.get();
        st.setName(student.getName());
        st.setAddress(student.getAddress());
        st.setBatch(student.getBatch());
        st.setAge(student.getAge());
        st.setCreatedAt(new Date());

        Students updatedStudent = studentRepository.save(st);

        kafkaTemplate.send(AppConstants.STUDENT_TOPIC_NAME, updatedStudent);

        return updatedStudent;
    }

    @Override
    public Students saveStudent(Students students) {

        Optional<Students> stu = studentRepository.findById(students.getStudentId());

        students.setCreatedAt(new Date());
        kafkaTemplate.send(AppConstants.STUDENT_TOPIC_NAME,students);
        return studentRepository.save(students);

    }

//    @KafkaListener(topics = AppConstants.NOTIFICATION_TOPIC_NAME,groupId = "notification_group")
//    public void listenNotificationData(Notifications notifications){
//
//        System.out.println(notifications);
//
//    }


    @Override
    public List<Notifications> getAllNotifications() {

        String url = "http://localhost:8082/notifications/getAllNotifications";
        return restTemplate.getForObject(url, List.class);

    }

    @Override
    public NotificationsPagewise getAllNotificationsPagewise(int pageNumber, int numberOfData) {
        String url = String.format("http://localhost:8082/notifications/getAllNotificationsPagewise?pageNumber=%d&numberOfData=%d", pageNumber, numberOfData);

        // Adjusted to directly map the JSON array to a List<Notifications>
        ResponseEntity<NotificationsPagewise> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<NotificationsPagewise>() {});
        return responseEntity.getBody();

    }

    @Override
    public NotificationsPagewise getAllNotificationsPagewiseSorted(int pageNumber, int numberOfData,String sortingField,String sortingDirection){

        // getting error when I am fetching data using kafka from notification side when the notification becomes producer as the data is coming from notification side

        return null;
    }

}
