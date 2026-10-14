package com.tp.queries.repository;

import com.tp.queries.model.Salle;

import java.time.LocalDateTime;
import java.util.List;

public interface SalleRepository {

    List<Salle> findAvailableRooms(LocalDateTime debutt, LocalDateTime finn);

    List<Salle> searchRooms(String nomm, Integer capaciteeMin, LocalDateTime debutt, LocalDateTime finn, int page, int size);

    long countRooms(String nomm, Integer capaciteeMin, LocalDateTime debutt, LocalDateTime finn);
}
