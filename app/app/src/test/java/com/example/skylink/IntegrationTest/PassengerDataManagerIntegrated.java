package com.example.skylink.IntegrationTest;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.example.skylink.TestUtils.TestUtils;
import com.example.skylink.application.Services;
import com.example.skylink.business.Implementations.PassengerDataManager;
import com.example.skylink.objects.Interfaces.iPassengerData;
import com.example.skylink.persistence.Interfaces.iBookingDB;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class PassengerDataManagerIntegrated {
    private iBookingDB bookingDatabase;
    private PassengerDataManager passengerDataManager;
    private File tempDB;
    @Before
    public void setUp() throws IOException {
        System.out.println("Starting integration test for Passenger Database");
        this.tempDB = TestUtils.copyDB();
        this.passengerDataManager = new PassengerDataManager(true);
        assertNotNull(this.passengerDataManager);
    }
    // General Test Cases

    @Test
    public void testAddBooking() {
        iPassengerData passengerData = passengerDataManager.addBooking("Mr.", "John", "Doe", "123456789", "john.doe@example.com");

        assertNotNull(passengerData);
        assertEquals("Mr.", passengerData.getTitle());
        assertEquals("John", passengerData.getFirstName());
        assertEquals("Doe", passengerData.getLastName());
        assertEquals("123456789", passengerData.getTelephoneNumber());
        assertEquals("john.doe@example.com", passengerData.getEmailAddress());
    }

    @Test
    public void testFindExistingBooking() {
        iPassengerData existingPassengerData = passengerDataManager.addBooking("Ms.", "Jane", "Smith", "987654321", "jane.smith@example.com");

        boolean found = passengerDataManager.findBooking("Ms.", "Jane", "Smith", "987654321", "jane.smith@example.com");

        assertTrue(found);
    }

    @Test
    public void testFindNonExistingBooking() {
        boolean found = passengerDataManager.findBooking("Mr.", "Non", "Existing", "555555555", "non.existing@example.com");

        assertFalse(found);
    }

    @Test
    public void testAddBookingWithNullEmail() {
        iPassengerData passengerData = passengerDataManager.addBooking("Ms.", "Alice", "Wonderland", "123456789", null);

        assertNull(passengerData);
    }

    @Test
    public void testAddBookingWithEmptyName() {
        iPassengerData passengerData = passengerDataManager.addBooking("Dr.", "", "", "987654321", "doctor@example.com");

        assertNotNull(passengerData);
        assertEquals("Dr.", passengerData.getTitle());
        assertEquals("", passengerData.getFirstName()); // Adjust based on your requirements
        assertEquals("", passengerData.getLastName());  // Adjust based on your requirements
        assertEquals("987654321", passengerData.getTelephoneNumber());
        assertEquals("doctor@example.com", passengerData.getEmailAddress());
    }

    @Test
    public void testFindBookingWithEmptyPhoneNumber() {
        iPassengerData existingPassengerData = passengerDataManager.addBooking("Prof.", "Charles", "Xavier", "", "professor@example.com");

        boolean found = passengerDataManager.findBooking("Prof.", "Charles", "Xavier", "", "professor@example.com");

        assertTrue(found);
    }

    @After
    public void tearDown() {
        System.out.println("Reset database.");
        // reset DB
        this.tempDB.delete();

        // clear Services
        Services.clean();
    }
}
