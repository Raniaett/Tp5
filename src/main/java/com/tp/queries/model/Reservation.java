package com.tp.queries.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salle_id", nullable = false)
    private Salle salle;

    @Column(nullable = false)
    private LocalDateTime debutt;

    @Column(nullable = false)
    private LocalDateTime finn;

    public Reservation() {
    }

    public Reservation(Salle salle, LocalDateTime debutt, LocalDateTime finn) {
        this.salle = salle;
        this.debutt = debutt;
        this.finn = finn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public LocalDateTime getdebutt() {
        return debutt;
    }

    public void setdebutt(LocalDateTime debutt) {
        this.debutt = debutt;
    }

    public LocalDateTime getfinn() {
        return finn;
    }

    public void setfinn(LocalDateTime finn) {
        this.finn = finn;
    }
}
