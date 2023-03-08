package com.example.BookMyShow.Service;

import com.example.BookMyShow.Entities.*;
import com.example.BookMyShow.EntryDtos.DeleteTicketEntryDto;
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

import java.util.*;

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
        int multiChecks = allocateSeats(seatToBeBook, showEntity);
        if(multiChecks == 0) throw new Exception("You have entered non existing seat number !");
        if(multiChecks == -1) throw new Exception("Some of the seats are already booked");

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
        ticketEntity.setShowEntity(showEntity);

        ticketRepository.save(ticketEntity);
        showRepository.save(showEntity);
        userRepository.save(user);



        String body = "Hi "+user.getUserName()+"\n\nThis is to confirm your ticket bookings please refer below details so you don't miss the show !!"+"\n\nMovie Name -"+showEntity.getMovieEntity().getMovieName();

        String info = "\nTheatre Name -"+showEntity.getTheatreEntity().getTheatreName()+"\nBooked Seats - "+bookedSeats+"\nTicket Id - "+ticketEntity.getTicketId()+"\nShow Date - "+showEntity.getLocalDate()+"\nShow Time - "+showEntity.getLocalTime();
        String greet = "\n\n Have a wonderful show !!  : - ) ";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom("krunalsolucky121@gmail.com");
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setText(body + info + greet);
        mimeMessageHelper.setSubject("Confirming your booked Ticket");

        javaMailSender.send(mimeMessage);

        return new ResponseEntity<>("Tickets booked successfully", HttpStatus.CREATED);
    }
    public int allocateSeats(List<String> seat, ShowEntity showEntity) {
        List<ShowSeatEntity> showEntityList = showEntity.getShowSeatEntityList();
        int count = 0;
        for(ShowSeatEntity showSeatEntity : showEntityList) {
            if(seat.contains(showSeatEntity.getSeatsNo())) {
                if(showSeatEntity.isBooked()) return -1;
                count ++;
            }
        }
        if(count != seat.size()) return 0;

        markSeatBooked(seat, showEntity);
        return 1;
    }

    private void markSeatBooked(List<String> seat, ShowEntity showEntity) {
        for(ShowSeatEntity currSeat : showEntity.getShowSeatEntityList()) {
            if(seat.contains(currSeat.getSeatsNo())) {
                currSeat.setBooked(true);
            }
        }
    }

    public ResponseEntity cancelTicket(DeleteTicketEntryDto deleteTicketEntryDto) throws Exception{
        TicketEntity ticketEntity = ticketRepository.findById(deleteTicketEntryDto.getTicketId()).get();
        List<ShowSeatEntity> showSeatEntityList = ticketEntity.getShowEntity().getShowSeatEntityList();
        List<String> ticketsToBeDeleted = deleteTicketEntryDto.getDeleteTicketList();

        String [] currTickets = ticketEntity.getBookedSeats().split(",");

        int count = 0;
        for(String ticketName : currTickets) if(ticketsToBeDeleted.contains(ticketName)) count ++;
        if(count != ticketsToBeDeleted.size()) throw new Exception("Please check the names of tickets to be deleted. Invalid data found !");

        HashSet<String> deletedTicketSet = new HashSet<>();
        for(ShowSeatEntity seat : showSeatEntityList) {
            if(ticketsToBeDeleted.contains(seat.getSeatsNo())) {
                seat.setBooked(false);
                deletedTicketSet.add(seat.getSeatsNo());
            }
        }

        String newBookedTickets = "";
        for(String tick : currTickets) {
            if(!deletedTicketSet.contains(tick)) {
                if(!newBookedTickets.isEmpty()) newBookedTickets += ',';
                newBookedTickets += tick;
            }
        }

        Iterator it = deletedTicketSet.iterator();
        String newTics = "";
        while(it.hasNext()) {
            newTics += it.next();
        }

        int toBeDeleted = deletedTicketSet.size() * 200;
        ticketEntity.setPrice(ticketEntity.getPrice() - toBeDeleted);

        UserEntity user = ticketEntity.getUserEntity();
        String body = "Hi "+user.getUserName()+"\n\nThis is to confirm your booking cancellation."+"\nTicket id - "+ticketEntity.getTicketId()+"\nCancelled Seats - "+newTics+"\nAmount of rupees - "+toBeDeleted+" will be refunded in to your account in 6-7 working days\n\n\n"+"Have a wonderful day !";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom("krunalsolucky121@gmail.com");
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject("Confirming your booked Ticket");

        javaMailSender.send(mimeMessage);


        if(newBookedTickets.isEmpty())  {
            ticketRepository.delete(ticketEntity);
            userRepository.save(user);
            return new ResponseEntity<>("Tickets has been successfully cancelled !", HttpStatus.OK);
        }

        ticketEntity.setBookedSeats(newBookedTickets);
        userRepository.save(ticketEntity.getUserEntity());

        return new ResponseEntity<>("Tickets has been successfully cancelled !", HttpStatus.OK);
    }
}