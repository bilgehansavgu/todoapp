package com.researchecosystems.todoapp.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Table(name = "tasks")
@Entity
@Getter
@Setter
public class Task extends Auditable {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private String Index;

    @Column(name = "description")
    private String description;

    @Column(name = "due")
    private ZonedDateTime due;

    @Column(name = "completed")
    private boolean completed;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}