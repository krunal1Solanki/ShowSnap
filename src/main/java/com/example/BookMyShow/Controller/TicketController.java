package com.example.BookMyShow.Controller;

import com.example.BookMyShow.EntryDtos.TicketEntryDto;
import com.example.BookMyShow.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ticekt")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @PostMapping("addTicket")
    public ResponseEntity addTicket(@RequestBody TicketEntryDto ticketEntryDto) throws Exception{
        try {
            return ticketService.addTicket(ticketEntryDto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
