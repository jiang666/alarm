package com.example.alarm.evenbus;

public class Event {

    private String messgae;

    public Event(String messgae) {
        this.messgae = messgae;
    }

    public String getMessgae() {
        return messgae;
    }
}