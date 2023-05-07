package com.platform.WebNode.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="CHAT")
public class Chat {

    @Id
    @Column(name = "CHAT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ID_FROM")
    private int idFrom;

    @Column(name = "ID_TO")
    private int idTo;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "DATE_TIME")
    private LocalDateTime dateTime;

    public Chat() {}

    public Chat(int idFrom, int idTo, String message, LocalDateTime dateTime) {
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.message = message;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public int getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(int idFrom) {
        this.idFrom = idFrom;
    }

    public int getIdTo() {
        return idTo;
    }

    public void setIdTo(int idTo) {
        this.idTo = idTo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", idFrom=" + idFrom +
                ", idTo=" + idTo +
                ", message='" + message + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
