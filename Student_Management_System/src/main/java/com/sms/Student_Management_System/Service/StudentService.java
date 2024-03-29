package com.sms.Student_Management_System.Service;

import com.sms.Student_Management_System.Model.Notifications;
import com.sms.Student_Management_System.Model.NotificationsPagewise;
import com.sms.Student_Management_System.Model.Students;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {

    public Students updateStudentById(int id,Students student);

    public Students saveStudent(Students students);

//    public void sendNotification(Students students);

    public List<Notifications> getAllNotifications();

    public NotificationsPagewise getAllNotificationsPagewise(int pageNumber, int numberOfData);

    public NotificationsPagewise getAllNotificationsPagewiseSorted(int pageNumber, int numberOfData,String sortingField,String sortingDirection);


}
