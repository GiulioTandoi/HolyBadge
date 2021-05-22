package it.holyfamily.holybadge.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inoutparish")
public class InOutParish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime entranceTime;
    private LocalDateTime exitTime;
    private int idParishioner;

    public InOutParish(LocalDateTime entranceTime, int idParishioner) {

        this.entranceTime = entranceTime;
        this.idParishioner = idParishioner;
    }

    public InOutParish() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getEntranceTime() {
        return entranceTime;
    }

    public void setEntranceTime(LocalDateTime entranceTime) {
        this.entranceTime = entranceTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public int getIdParishioner() {
        return idParishioner;
    }

    public void setIdParishioner(int idParishioner) {
        this.idParishioner = idParishioner;
    }

}
