package com.example.microservices.todomicroservices.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "todos")
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "description")
    private @NotNull @NotEmpty @NotBlank String description;

    @Column(name = "date")
    private Date date;

    @Column(name = "priority")
    private @NotNull @NotEmpty @NotBlank String priority;

    @Column(name = "user")
    private @NotNull @NotEmpty @NotBlank String user;

    public ToDo() {
    }

    public ToDo(Integer id, String description, Date date, String priority, String user) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.priority = priority;
        this.user = user;
    }

    @PrePersist
    void getTimeOperation() {
        date = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}