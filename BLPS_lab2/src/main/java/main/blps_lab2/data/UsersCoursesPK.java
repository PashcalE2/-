package main.blps_lab2.data;

import jakarta.persistence.Id;

import java.io.Serializable;

public class UsersCoursesPK implements Serializable {
    @Id
    private Long userId;
    @Id
    private Long courseId;
}
