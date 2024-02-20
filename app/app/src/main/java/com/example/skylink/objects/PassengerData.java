package com.example.skylink.objects;

public class PassengerData {
    private String title;
    private String first_name;
    private String last_name;
    private String telephone_number;
    private String email_address;

    public PassengerData(String title, String first_name, String last_name, String telephone_number, String email_address) {
        this.title = title;
        this.first_name = first_name;
        this.last_name = last_name;
        this.telephone_number = telephone_number;
        this.email_address = email_address;
    }

    // Getter methods for the booking details
    public String getTitle() {
        return title;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getTelephoneNumber() {
        return telephone_number;
    }

    public String getEmailAddress() {
        return email_address;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setTelephone_number(String telephone_number) {
        this.telephone_number = telephone_number;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

}