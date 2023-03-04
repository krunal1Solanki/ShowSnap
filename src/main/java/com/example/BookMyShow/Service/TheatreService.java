package com.example.BookMyShow.Service;

import com.example.BookMyShow.Convertors.TheatreEntryConverter;
import com.example.BookMyShow.Entities.ShowSeatEntity;
import com.example.BookMyShow.Entities.TheatreEntity;
import com.example.BookMyShow.Entities.TheatreSeatEntity;
import com.example.BookMyShow.EntryDtos.TheatreEntryDto;
import com.example.BookMyShow.Enums.SeatTypes;
import com.example.BookMyShow.Repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TheatreService {
    @Autowired
    TheatreRepository theatreRepository;
    public ResponseEntity addTheatre(TheatreEntryDto theatreEntryDto) {
        TheatreEntity theatreEntity = TheatreEntryConverter.entryConverter(theatreEntryDto);
        int reclinerSeat = theatreEntryDto.getReclinerSeats();
        int classicSeat = theatreEntryDto.getStandardSeats();
        int getVipSeats = theatreEntryDto.getVipSeats();
        int sofaSeats = theatreEntryDto.getSofaSeats();

        List<TheatreSeatEntity> seatList = getTheatreSeatList(reclinerSeat, classicSeat, getVipSeats, sofaSeats, theatreEntity);
        theatreEntity.setTheatreSeatEntityList(seatList);
        theatreRepository.save(theatreEntity);

        return new ResponseEntity<>("Theatre Added Successfully", HttpStatus.CREATED);
    }

    private List<TheatreSeatEntity> getTheatreSeatList(int reclinerSeatCount, int classicSeatCount, int vipSeatCount, int sofaSeats, TheatreEntity theatre) {
        List<TheatreSeatEntity> theatreSeatEntityList = new ArrayList<>();
        for(int i = 1; i <= reclinerSeatCount; i ++) {
            TheatreSeatEntity seat = new TheatreSeatEntity();
            seat.setSeatType(SeatTypes.Recliner_Seats);
            seat.setSeatNo(i + "Rec");
            seat.setTheatreEntity(theatre);
            theatreSeatEntityList.add(seat);
        }

        for(int i = 1; i <= classicSeatCount; i ++) {
            TheatreSeatEntity seat = new TheatreSeatEntity();
            seat.setSeatType(SeatTypes.Standard_Seats);
            seat.setSeatNo(i + "Std");
            seat.setTheatreEntity(theatre);
            theatreSeatEntityList.add(seat);
        }
        for(int i = 1; i <= sofaSeats; i ++) {
            TheatreSeatEntity seat = new TheatreSeatEntity();
            seat.setSeatType(SeatTypes.Sofa_Seats);
            seat.setSeatNo(i + "Sofa");
            seat.setTheatreEntity(theatre);
            theatreSeatEntityList.add(seat);
        }

        for(int i = 1; i <= vipSeatCount; i ++) {
            TheatreSeatEntity seat = new TheatreSeatEntity();
            seat.setSeatType(SeatTypes.VIP_Seats);
            seat.setSeatNo(i+"Vip");
            seat.setTheatreEntity(theatre);
            theatreSeatEntityList.add(seat);
        }

        return theatreSeatEntityList;
    }
}
