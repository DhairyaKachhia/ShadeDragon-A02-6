package com.example.skylink.persistence.Interfaces;

import com.example.skylink.objects.Interfaces.iFlightInfo;
import com.example.skylink.objects.Interfaces.iPassengerData;

import java.util.HashMap;

public interface iFlightBookingDB {
    void addFlightBooking(long user_id, String bound,  iFlightInfo flightInfo, int price, String bookingNumber);
    iFlightBookingDB initialize();
    iFlightBookingDB drop();
}
