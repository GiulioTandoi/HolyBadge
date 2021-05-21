package it.holyfamily.holybadge.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movements")
public class Movements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime entranceTime;
    private LocalDateTime exitTime;
    private int idParishioner;
    private int idMeeting;

    public Movements() {
    }

    public LocalDateTime getEntranceTime() {
        return entranceTime;
    }

    public void setEntranceTime(LocalDateTime ingresso) {
        this.entranceTime = ingresso;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime uscita) {
        this.exitTime = uscita;
    }

    public int getIdParishioner() {
        return idParishioner;
    }

    public void setIdParishioner(int idParishioner) {
        this.idParishioner = idParishioner;
    }

    public int getIdMeeting() {
        return idMeeting;
    }

    public void setIdMeeting(int idMeeting) {
        this.idMeeting = idMeeting;
    }

}
