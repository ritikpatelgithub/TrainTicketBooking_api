package com.example.train.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SeatAllocator {
    private List<String> availableSeatsA = new ArrayList<>();
    private List<String> availableSeatsB = new ArrayList<>();
    private Random random = new Random();

    public SeatAllocator() {
        // Initialize available seats for sections A and B
        for (int i = 1; i <= 20; i++) {
            availableSeatsA.add("A" + i);
            availableSeatsB.add("B" + i);
        }
    }

    public String allocateRandomSeat() {
        if (!availableSeatsA.isEmpty() && !availableSeatsB.isEmpty()) {
            return random.nextBoolean() ? allocateSeatFromList(availableSeatsA) : allocateSeatFromList(availableSeatsB);
        } else if (!availableSeatsA.isEmpty()) {
            return allocateSeatFromList(availableSeatsA);
        } else if (!availableSeatsB.isEmpty()) {
            return allocateSeatFromList(availableSeatsB);
        } else {
            throw new RuntimeException("No seats available");
        }
    }

    public void releaseSeat(String seat) {
        if (seat.startsWith("A")) {
            availableSeatsA.add(seat);
        } else if (seat.startsWith("B")) {
            availableSeatsB.add(seat);
        }
    }

    private String allocateSeatFromList(List<String> seatList) {
        int randomIndex = random.nextInt(seatList.size());
        return seatList.remove(randomIndex);
    }
}
