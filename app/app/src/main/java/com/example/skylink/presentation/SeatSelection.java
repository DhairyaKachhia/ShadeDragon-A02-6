package com.example.skylink.presentation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skylink.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SeatSelection extends AppCompatActivity {

    private Map<String, SeatStatus> seatStatusMap = new HashMap<>();
    private List<String> selectedSeats = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_selection);

        String[][] planeConfigurations = {
                {"Boeing 737", "4", "6", "6", "18"},
                {"Airbus A320", "4", "7", "6", "14"},
                {"Embraer E190", "4", "4", "6", "10"},
                {"Boeing 777", "4", "8", "6", "120"},
                {"Bombardier Q400", "4", "4", "6", "8"}
        };

        // Initialize your layout or perform any other setup if needed

        // Add seats to Flight_Layout
        addSeatsToLayout(planeConfigurations[2]);
    }

    private void addSeatsToLayout(String[] planeConfig) {
        LinearLayout flightLayout = findViewById(R.id.Flight_Layout);

        String numSeatBusi, numRowBusi, numSeatEcon, numRowEcon;
        numSeatBusi = planeConfig[1];
        numRowBusi = planeConfig[2];
        numSeatEcon = planeConfig[3];
        numRowEcon = planeConfig[4];

        addClassSeatsToLayout(flightLayout, Integer.parseInt(numRowBusi), Integer.parseInt(numSeatBusi), 1, true);

        addClassSeatsToLayout(flightLayout, Integer.parseInt(numRowEcon), Integer.parseInt(numSeatEcon), 6, false);
    }

    private void addClassSeatsToLayout(LinearLayout flightLayout, int numRows, int numSeatsPerRow, int startSeatNumber, boolean isBusinessClass) {
        Random random = new Random();

        for (int row = startSeatNumber; row < startSeatNumber + numRows; row++) {
            LinearLayout rowLayout = new LinearLayout(this);
            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setTag(String.valueOf(row)); // Set the row as a tag

            int halfNumSeatsPerRow = numSeatsPerRow / 2;

            for (int seatNumber = 1; seatNumber <= halfNumSeatsPerRow; seatNumber++) {
                boolean isSeatTaken = random.nextBoolean();
                addSeatToLayout(rowLayout, row, seatNumber, isBusinessClass, isSeatTaken);
                // Save the seat status in the Map
                seatStatusMap.put(getSeatKey(row, seatNumber), new SeatStatus(isBusinessClass, isSeatTaken));
            }

            // Add aisle
            addAisleToLayout(rowLayout);

            for (int seatNumber = halfNumSeatsPerRow + 1; seatNumber <= halfNumSeatsPerRow * 2; seatNumber++) {
                boolean isSeatTaken = random.nextBoolean();
                addSeatToLayout(rowLayout, row, seatNumber, isBusinessClass, isSeatTaken);
                // Save the seat status in the Map
                seatStatusMap.put(getSeatKey(row, seatNumber), new SeatStatus(isBusinessClass, isSeatTaken));
            }

            flightLayout.addView(rowLayout);
        }
    }

    @SuppressLint("SetTextI18n")
    private void addSeatToLayout(LinearLayout flightLayout, int row, int seatNumber, boolean isBusinessClass, boolean isSeatTaken) {
        // Create a vertical linear layout to contain the seat image and seat number
        LinearLayout seatContainer = new LinearLayout(this);
        seatContainer.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        seatContainer.setOrientation(LinearLayout.VERTICAL);

        // Create the TextView for the seat number
        TextView seatNumberText = new TextView(this);
        seatNumberText.setText(String.valueOf(row) + getSeatLetter(seatNumber));
        seatNumberText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        seatContainer.addView(seatNumberText);

        // Create the ImageView for the seat image
        ImageView seatImage = new ImageView(this);
        LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(
                150, 150); // Adjust the width and height as needed
        imageLayoutParams.setMargins(25, 25, 25, 25); // Adjust margins as needed
        seatImage.setLayoutParams(imageLayoutParams);

        // Set the seat image based on whether it's a business class seat and whether it's taken
        if (isBusinessClass) {
            seatImage.setImageResource(R.drawable.firstclass_seat);
        } else {
            seatImage.setImageResource(R.drawable.economy_seat);
        }

        // Check if the seat is taken
        if (isSeatTaken) {
            seatImage.setColorFilter(getResources().getColor(R.color.redTint)); // Replace R.color.redTint with the actual resource ID of your red color
        } else {
            seatImage.setColorFilter(null); // Remove any existing color filter
        }

        seatImage.setOnClickListener(view -> toggleSeat(seatImage, row, seatNumber));
        seatContainer.addView(seatImage);

        // Add the vertical linear layout to the main layout (flightLayout)
        flightLayout.addView(seatContainer);
    }

    // Function to get a unique key for each seat based on row and seatNumber
    private String getSeatKey(int row, int seatNumber) {
        return row + "_" + seatNumber;
    }

    private void toggleSeat(ImageView seatContainer, int row, int seatNumber) {
        String seatKey = getSeatKey(row, seatNumber);

        SeatStatus seatStatus = seatStatusMap.get(seatKey);

        if (seatStatus == null || seatStatus.isTaken()) {
            return;
        }

        int seatType = seatStatus.isBusinessClass() ? R.drawable.firstclass_seat : R.drawable.economy_seat;
        boolean isSelected = seatContainer.isSelected();
        // Change the background image and tint based on the selected state
        seatContainer.setSelected(!isSelected);
        selectedSeats.add(row+getSeatLetter(seatNumber));

        if (isSelected) {
            // If already selected, set the default seat image with no tint
            seatContainer.setImageResource(seatType);
            seatContainer.setColorFilter(null); // Remove any existing color filter
        } else {
            // If not selected, set the seat image and apply red tint
            seatContainer.setImageResource(seatType);
            seatContainer.setColorFilter(getResources().getColor(R.color.greenColor)); // Replace R.color.redTint with the actual resource ID of your red color
        }
    }

    private void addAisleToLayout(LinearLayout flightLayout) {
        View aisleView = new View(this);

        // Set layout parameters for a horizontal aisle
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                80, // Aisle width
                LinearLayout.LayoutParams.MATCH_PARENT);
        aisleView.setLayoutParams(layoutParams);

        aisleView.setBackgroundColor(getResources().getColor(android.R.color.white)); // Aisle color
        flightLayout.addView(aisleView);
    }

    private String getSeatLetter(int seatNumber) {
        char seatLetter = (char) ('A' + (seatNumber - 1));
        return String.valueOf(seatLetter);
    }

    // Class to represent seat status
    private static class SeatStatus {
        private boolean isBusinessClass;
        private boolean isTaken;

        SeatStatus(boolean isBusinessClass, boolean isTaken) {
            this.isBusinessClass = isBusinessClass;
            this.isTaken = isTaken;
        }

        boolean isBusinessClass() {
            return isBusinessClass;
        }

        boolean isTaken() {
            return isTaken;
        }
    }
}