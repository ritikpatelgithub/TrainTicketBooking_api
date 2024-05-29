package com.example.train.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.train.model.Ticket;
import com.example.train.model.User;
import com.example.train.service.SeatAllocator;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private Map<String, Ticket> tickets = new HashMap<>();
    private SeatAllocator seatAllocator = new SeatAllocator();

    @PostMapping("/purchase")
    public Ticket purchaseTicket(@RequestBody User user) {
        String seat = seatAllocator.allocateRandomSeat();
        Ticket ticket = new Ticket("London", "France", user, 20.0, seat);
        tickets.put(user.getEmail(), ticket);
        return ticket;
    }

    @GetMapping("/receipt/{email}")
    public Ticket getReceipt(@PathVariable String email) {
        return tickets.get(email);
    }

    @GetMapping("/section/{section}")
    public List<Ticket> getTicketsBySection(@PathVariable String section) {
        List<Ticket> sectionTickets = new ArrayList<>();
        for (Ticket ticket : tickets.values()) {
            if (ticket.getSeat().startsWith(section)) {
                sectionTickets.add(ticket);
            }
        }
        return sectionTickets;
    }

    @DeleteMapping("/remove/{email}")
    public String removeUser(@PathVariable String email) {
        Ticket ticket = tickets.remove(email);
        if (ticket != null) {
            seatAllocator.releaseSeat(ticket.getSeat());
        }
        return "User removed";
    }

    @PutMapping("/modify/{email}")
    public Ticket modifySeat(@PathVariable String email, @RequestParam String newSeat) {
        Ticket ticket = tickets.get(email);
        if (ticket != null) {
            seatAllocator.releaseSeat(ticket.getSeat());
            ticket.setSeat(newSeat);
        }
        return ticket;
    }
}
