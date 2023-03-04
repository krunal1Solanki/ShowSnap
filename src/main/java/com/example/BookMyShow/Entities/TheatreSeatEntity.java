package com.example.BookMyShow.Entities;

import com.example.BookMyShow.Enums.SeatTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;
@Entity
@Table(name = "theatreSeat")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TheatreSeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(value = EnumType.STRING)
    private SeatTypes seatType;

    private String seatNo;

    @ManyToOne
    @JoinColumn
    private TheatreEntity theatreEntity;
}
