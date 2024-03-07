package com.example.skylink.persistence.Implementations.hsqldb;

import com.example.skylink.objects.Interfaces.iPassengerData;
import com.example.skylink.persistence.Interfaces.iBookingDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookingHSQLDB implements iBookingDB {

    private final String dbPath;

    private final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS BOOKINGS ("
            + "id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,"
            + "title VARCHAR(10) NOT NULL,"
            + "first_name VARCHAR(100) NOT NULL,"
            + "last_name VARCHAR(100) NOT NULL,"
            + "telephone_number VARCHAR(20) NOT NULL,"
            + "email_address VARCHAR(100) NOT NULL"
            + ")";

    public BookingHSQLDB(String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    @Override
    public void addBooking(iPassengerData passengerData) {
        String sql = "INSERT INTO BOOKINGS (title, first_name, last_name, telephone_number, email_address) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, passengerData.getTitle());
            ps.setString(2, passengerData.getFirstName());
            ps.setString(3, passengerData.getLastName());
            ps.setString(4, passengerData.getTelephoneNumber());
            ps.setString(5, passengerData.getEmailAddress());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean findBooking(iPassengerData searchPassengerData) {
        String sql = "SELECT * FROM BOOKINGS WHERE title=? AND first_name=? AND last_name=? AND telephone_number=? AND email_address=?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, searchPassengerData.getTitle());
            ps.setString(2, searchPassengerData.getFirstName());
            ps.setString(3, searchPassengerData.getLastName());
            ps.setString(4, searchPassengerData.getTelephoneNumber());
            ps.setString(5, searchPassengerData.getEmailAddress());

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Returns true if the result set is not empty
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public iBookingDB initialize() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            this.drop();
            stmt.executeUpdate(CREATE_TABLE);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public iBookingDB drop() {
        String sql = "DROP TABLE USER";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }
}
