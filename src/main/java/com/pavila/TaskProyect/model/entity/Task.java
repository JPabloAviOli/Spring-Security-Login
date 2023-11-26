package com.pavila.TaskProyect.model.entity;

import com.pavila.TaskProyect.model.entity.security.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "finished_date")
    private String finishedDate;

    private String priority;
    private boolean completed;
    private String assignee;

    @ManyToOne(targetEntity = User.class)
    private User createdByUser;

    private String category;

}
