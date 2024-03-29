package com.notificationservice.studentnotificationservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Students {

    @Id
    private int studentId;

    private String name;

    private int age;

    private String batch;

    private String address;

    @CreatedDate
    private Date createdAt;

}
