package com.sms.Student_Management_System.Model;

import lombok.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationsPagewise {

    private List<Notifications> content;
    private Pageable pageable;
    private boolean last;
    private int totalPages;
    private long totalElements;
    private int size;
    private int number;
    private Sort sort;
    private boolean first;
    private int numberOfElements;
    private boolean empty;


}
