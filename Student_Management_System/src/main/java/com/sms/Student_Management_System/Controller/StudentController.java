package com.sms.Student_Management_System.Controller;

import com.sms.Student_Management_System.AppConstants;
import com.sms.Student_Management_System.Model.Notifications;
import com.sms.Student_Management_System.Model.NotificationsPagewise;
import com.sms.Student_Management_System.Model.Students;
import com.sms.Student_Management_System.Repository.StudentRepository;
import com.sms.Student_Management_System.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    public StudentRepository studentRepository;

    @Autowired
    public StudentService studentService;

    @Autowired
    private KafkaTemplate<String, Students> kafkaTemplate;

    @PostMapping("/addStudent")
    public ResponseEntity<Students> addStudent(@RequestBody Students students) throws Exception {

        Students st = studentService.saveStudent(students);

        return new ResponseEntity<>(st, HttpStatus.OK);
    }

    @GetMapping("getStudentById/{id}")
    public ResponseEntity<Students> getStudentById(@PathVariable int id){

        Optional<Students> s = studentRepository.findById(id);

        return new ResponseEntity<>(s.get(),HttpStatus.OK);

    }

    @GetMapping("/getAllStudents")
    public ResponseEntity<List<Students>> getAllStudents(){

        List<Students> st = studentRepository.findAll();

        return new ResponseEntity<>(st,HttpStatus.OK);
    }


    @PutMapping("/updateStudentById/{id}")
    public ResponseEntity<Students> updateStudentById(@PathVariable int id,@RequestBody Students students){

        Students st = studentService.updateStudentById(id,students);

        return new ResponseEntity<>(st,HttpStatus.OK);
    }


    @DeleteMapping("/deleteStudentById/{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable int id){

        Optional<Students> optionalStudent = studentRepository.findById(id);

        if(optionalStudent.isEmpty()){
            return new ResponseEntity<>("Student not found with id : " + id, HttpStatus.NOT_FOUND);
        }

        Students student = optionalStudent.get();

        kafkaTemplate.send(AppConstants.STUDENT_TOPIC_NAME, student);

        return new ResponseEntity<>("Student marked as deleted with id: " + id, HttpStatus.OK);


    }


    @GetMapping("/getAllNotifications")
    public ResponseEntity<List<Notifications>> getAllNotifications(){

        List<Notifications> notifications = studentService.getAllNotifications();

        return new ResponseEntity<>(notifications,HttpStatus.OK);
    }

    @GetMapping("/getAllNotificationsPagewise")
    public NotificationsPagewise getAllNotificationsPagewise(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int numberOfData){

        NotificationsPagewise notifications = studentService.getAllNotificationsPagewise(pageNumber,numberOfData);

        return notifications;

    }



}
