package com.example.BookMyShow.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "ticket")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int price;
    private LocalDate showDate;
    private LocalTime showTime;
    private String ticketId = UUID.randomUUID().toString();
    private String theatreName;
    private String movieName;

    private String bookedSeats;

    @JoinColumn
    @ManyToOne
    private UserEntity userEntity;
    @JoinColumn
    @ManyToOne
    private ShowEntity showEntity;
}
