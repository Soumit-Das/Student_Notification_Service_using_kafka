package com.sms.Student_Management_System.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notifications {

    @Id
    private int notificationId;
    private String notificationType;
    private Students payload;
    private String createdOn;

}
