package com.example.BookMyShow.Repository;

import com.example.BookMyShow.Entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<TicketEntity, Integer> {
}
