package com.example.skylink.presentation.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skylink.R;
import com.example.skylink.business.Implementations.PaymentHandler;
import com.example.skylink.business.Implementations.Session;
import com.example.skylink.business.Interface.IPaymentHandler;
import com.example.skylink.business.Interface.ISession;
import com.example.skylink.objects.Implementations.TripInvoice;
import com.example.skylink.objects.Interfaces.ITripInvoice;
import com.example.skylink.presentation.FlightSearching.FlightSearch;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentSuccessfulActivity extends AppCompatActivity {
    private final ISession session = Session.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_successful);

        updateReview();

        IPaymentHandler paymentHandler = new PaymentHandler();
        ITripInvoice tripInvoice = new TripInvoice(session.getUser_id(), session.getTotalPrice());

        boolean addSuccess = paymentHandler.addPayment(tripInvoice);

        Button buttonMainMenu = findViewById(R.id.buttonMainMenu);
        buttonMainMenu.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentSuccessfulActivity.this, FlightSearch.class);
            startActivity(intent);
            finish();
        });
    }

    private void updateReview() {
        TextView payeeName, transactionDate, totalAmount;

        payeeName = findViewById(R.id.payeeName);
        transactionDate = findViewById(R.id.transactionDate);
        totalAmount = findViewById(R.id.totalAmount);

        payeeName.setText(session.getCardholderName());


        // Get the current date
        Date currentDate = new Date();

        // Create a formatter for the desired format
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");

        // Format the current date using the formatter
        transactionDate.setText(formatter.format(currentDate));


        totalAmount.setText("" + session.getTotalPrice());

    }
}
