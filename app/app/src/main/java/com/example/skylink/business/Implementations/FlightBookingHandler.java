package com.example.skylink.business.Implementations;

import com.example.skylink.application.Services;
import com.example.skylink.business.Interface.iFlightBookingHandler;
import com.example.skylink.objects.Implementations.BookingInfo;
import com.example.skylink.objects.Implementations.Flight;
import com.example.skylink.objects.Implementations.FlightInfo;
import com.example.skylink.objects.Interfaces.iBookingInfo;
import com.example.skylink.objects.Interfaces.iFlight;
import com.example.skylink.objects.Interfaces.iFlightInfo;
import com.example.skylink.objects.Interfaces.iPassengerData;
import com.example.skylink.persistence.Interfaces.iBookingDB;
import com.example.skylink.persistence.Interfaces.iFlightBookingDB;
import com.example.skylink.persistence.Interfaces.IFlightDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FlightBookingHandler implements iFlightBookingHandler {
    private iFlightBookingDB flightBookingDB;
    private iBookingDB bookingDB;
    private IFlightDB flightDB;

    public FlightBookingHandler(iFlightBookingDB flightBookingDB, iBookingDB bookingDB, IFlightDB flightDB) {
        this.flightBookingDB = flightBookingDB;
        this.bookingDB = bookingDB;
        this.flightDB = flightDB;
    }

    public FlightBookingHandler(boolean forProduction) {
        this.flightBookingDB = Services.getFlightBookingDB();
        this.bookingDB = Services.getBookDatabase();
        this.flightDB = Services.getFlightDatabase();

    }

    public String addConfirmBookings(long user_id, List<iFlightInfo> flightInfo) {
        String bookingNumber = generateBookingNumber();

        if (flightInfo != null) {
            for(iFlightInfo flightInfoEntry : flightInfo){
                if(flightInfoEntry != null){
                    int price = calculateTotalPrice(flightInfoEntry);
                    if(isValidDirection(flightInfoEntry.getBound())){
                        for (iFlight flight : flightInfoEntry.getFlight()) {
                            flightBookingDB.addFlightBooking(user_id, flightInfoEntry.getBound(), flight, price, bookingNumber, flightInfoEntry.getEconOrBus(), flightInfoEntry.getBagCount(), flightInfoEntry.getPetCount(), flightInfoEntry.getWifiOption(), flightInfoEntry.getWheelchairOption());
                        }
                    }
                }

            }
            iFlightInfo flight = flightInfo.get(0);
            if(flight != null){
                for (Map.Entry<iPassengerData, String> passengerEntry : flight.getSeatSelected().entrySet()) {
                    bookingDB.updateBookingInformation(passengerEntry.getKey().getEmailAddress(), user_id, bookingNumber, passengerEntry.getValue());
                }
            }

        }
        return bookingNumber;
    }

    private boolean isValidDirection(String direction) {
        return "Outbound".equals(direction) || "Inbound".equals(direction);
    }


    public List<iFlightInfo> getBookingDetails(long userID) {
        List<iFlightInfo> bookingDetailsList = new ArrayList<>();

        // Get all booking numbers for the user
        List<iBookingInfo> bookingNumbers = flightBookingDB.getBookingInfoByUserId(userID);

        for (iBookingInfo bookingInfo : bookingNumbers) {
            // Preapare information about the flight for the user.
            iFlightInfo flightInfo = prepareFlightInfo(bookingInfo);
            if(flightInfo != null){
//                Add the booking number
                flightInfo.setBookingNum(bookingInfo.getBookingNumber());
                bookingDetailsList.add(flightInfo);
            }
        }

        return bookingDetailsList.isEmpty() ? null : bookingDetailsList;
    }


    private iFlightInfo prepareFlightInfo(iBookingInfo bookingInfo) {
        iFlightInfo flightInfo = new FlightInfo();
        flightInfo.setEconOrBus(bookingInfo.getEconBus());
        flightInfo.setBound(bookingInfo.getDirection());
        List<String> flightBookingBound = flightBookingDB.getFlightsByUserId(bookingInfo.getDirection(),bookingInfo.getBookingNumber());

        if (flightBookingBound.isEmpty()) {
            return null;
        }

        List<iFlight> flightBound = flightDB.getFlightsByFlightNumbers(flightBookingBound);
        flightInfo.setFlight(flightBound);

        HashMap<iPassengerData, String> passengers = bookingDB.getPassengersWithSeatNumbers(bookingInfo.getBookingNumber());
        if(passengers.isEmpty()){
            return null;
        }
        flightInfo.setSeatSelected(passengers);

        return flightInfo;
    }




    private String generateBookingNumber() {
        String bookingNumber = UUID.randomUUID().toString().replaceAll("-", "");
        bookingNumber = bookingNumber.substring(0, 5);
        return bookingNumber;
    }

    private int calculateTotalPrice(iFlightInfo flightInfo) {
        int price = 0;
        int unitCost = 0;

        if (flightInfo.getEconOrBus().equals("Economy")) {
            for (iFlight flight : flightInfo.getFlight()) {
                unitCost += flight.getEconPrice();
            }
            price = flightInfo.getSeatSelected().size() * unitCost;
        } else if (flightInfo.getEconOrBus().equals("Business")) {
            for (iFlight flight : flightInfo.getFlight()) {
                unitCost += flight.getBusnPrice();
            }
            price = flightInfo.getSeatSelected().size() * unitCost;
        }

        return price;
    }


}
