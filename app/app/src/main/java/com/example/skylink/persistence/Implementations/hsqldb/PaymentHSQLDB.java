package com.example.skylink.persistence.Implementations.hsqldb;

import com.example.skylink.objects.Interfaces.ITripInvoice;
import com.example.skylink.persistence.Interfaces.IPaymentDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PaymentHSQLDB implements IPaymentDB {

    private final String dbPath;

    private final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS PAYMENT ("
            + "paymentId INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,"
            + "userID INT,"
            + "totalAmount INT NOT NULL,"
            + "FOREIGN KEY (userID) REFERENCES USER (id)"
            + ")";

    public PaymentHSQLDB(String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    @Override
    public boolean addPayment(ITripInvoice tripInvoice) {
        String sql = "INSERT INTO PAYMENT (userID, totalAmount) VALUES (?, ?)";

        long userID = tripInvoice.getUserID();
        int totalAmount = tripInvoice.getTotalAmount();

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.NO_GENERATED_KEYS)) {

            ps.setLong(1, userID);
            ps.setInt(2, totalAmount);

            int rowsAffected = ps.executeUpdate();

            // Check if the update was successful
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }

    }

    @Override
    public IPaymentDB initialize() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(CREATE_TABLE);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public IPaymentDB drop() {
        String sql = "DROP TABLE PAYMENT";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

}
