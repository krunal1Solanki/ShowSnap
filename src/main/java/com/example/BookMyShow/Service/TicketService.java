package com.example.BookMyShow.Service;

import com.example.BookMyShow.Entities.*;
import com.example.BookMyShow.EntryDtos.TicketEntryDto;
import com.example.BookMyShow.Repository.ShowRepository;
import com.example.BookMyShow.Repository.TheatreRepository;
import com.example.BookMyShow.Repository.TicketRepository;
import com.example.BookMyShow.Repository.UserRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    @Autowired
    ShowRepository showRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    JavaMailSender javaMailSender;
    public ResponseEntity addTicket(TicketEntryDto ticketEntryDto) throws Exception{
        TicketEntity ticketEntity = new TicketEntity();
        ShowEntity showEntity = showRepository.findById(ticketEntryDto.getShowId()).get();
        ticketEntity.setMovieName(showEntity.getMovieEntity().getMovieName());

        List<String> seatToBeBook = ticketEntryDto.getSeatToBeBook();
        boolean isBooked = allocateSeats(seatToBeBook, showEntity);

        if(!isBooked) throw new Exception("Some of the seats are already booked");

        String bookedSeats = "";
        for(String s : seatToBeBook) {
            if(!bookedSeats.isEmpty()) bookedSeats += ',';
            bookedSeats += s;
        }

        ticketEntity.setBookedSeats(bookedSeats);
        ticketEntity.setPrice(seatToBeBook.size() * showEntity.getSeatPrice());
        ticketEntity.setShowDate(showEntity.getLocalDate());
        ticketEntity.setShowTime(showEntity.getLocalTime());
        ticketEntity.setTheatreName(showEntity.getTheatreEntity().getTheatreName());

        showEntity.getTicketEntityList().add(ticketEntity);
        ticketEntity.setShowEntity(showEntity);

        UserEntity user = userRepository.findById(ticketEntryDto.getShowId()).get();
        user.getBookedTicketList().add(ticketEntity);
        ticketEntity.setUserEntity(user);

        ticketRepository.save(ticketEntity);
        showRepository.save(showEntity);
        userRepository.save(user);



        String body = "Hi this is to confirm your booking for seat No "+ bookedSeats +"for the movie : " + ticketEntity.getMovieName();


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom("backeendacciojob@gmail.com");
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject("Confirming your booked Ticket");

        javaMailSender.send(mimeMessage);

        return new ResponseEntity<>("Tickets booked successfully", HttpStatus.CREATED);
    }
    public boolean allocateSeats(List<String> seat, ShowEntity showEntity) {
        List<ShowSeatEntity> showEntityList = showEntity.getShowSeatEntityList();
        for(ShowSeatEntity currSeat : showEntityList) {
            if(seat.contains(currSeat.getSeatsNo()) && currSeat.isBooked()) {
                return false;
            }
        }

        markSeatBooked(seat, showEntity);
        return true;
    }

    private void markSeatBooked(List<String> seat, ShowEntity showEntity) {
        for(ShowSeatEntity currSeat : showEntity.getShowSeatEntityList()) {
            if(seat.contains(currSeat.getSeatsNo())) {
                currSeat.setBooked(true);
            }
        }
    }
}