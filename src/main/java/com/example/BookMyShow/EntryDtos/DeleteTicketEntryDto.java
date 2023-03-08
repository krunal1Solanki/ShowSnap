package com.example.BookMyShow.EntryDtos;

import lombok.Data;

import java.util.List;

@Data
public class DeleteTicketEntryDto {
    private int ticketId;
    private List<String> deleteTicketList;
}
