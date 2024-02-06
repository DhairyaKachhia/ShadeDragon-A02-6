package com.example.skylink.objects;

public class Booking {
    private String name;
    private String destination;
    private String date;

    public Booking(String name, String destination, String date) {
        this.name = name;
        this.destination = destination;
        this.date = date;
    }

    // Getter methods for the booking details
    public String getName() {
        return name;
    }

    public String getDestination() {
        return destination;
    }

    public String getDate() {
        return date;
    }
}