package com.example.train;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.train.model.Ticket;
import com.example.train.model.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TrainTicketBookingApplicationTests {


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void purchaseTicketTest() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");

        ResponseEntity<Ticket> response = restTemplate.postForEntity("/api/tickets/purchase", user, Ticket.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getUser().getFirstName()).isEqualTo("John");
    }

    @Test
    public void getReceiptTest() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        restTemplate.postForEntity("/api/tickets/purchase", user, Ticket.class);

        ResponseEntity<Ticket> response = restTemplate.getForEntity("/api/tickets/receipt/john.doe@example.com", Ticket.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getUser().getEmail()).isEqualTo("john.doe@example.com");
    }
}
