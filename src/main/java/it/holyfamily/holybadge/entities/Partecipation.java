package it.holyfamily.holybadge.entities;

import com.sun.istack.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "partecipation")
public class Partecipation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer idParishioner;
    private Integer idMeeting;
    @Nullable
    private LocalDateTime partecipated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public LocalDateTime getPartecipated() {
        return partecipated;
    }

    public void setPartecipated(LocalDateTime partecipated) {
        this.partecipated = partecipated;
    }

}
