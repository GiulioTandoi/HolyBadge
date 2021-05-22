package it.holyfamily.holybadge.entities;

import javax.persistence.*;

@Entity
@Table(name = "partecipation")
public class Partecipation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int idParishioner;
    private int idMeeting;

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


}
