package it.holyfamily.holybadge.pojos;

import it.holyfamily.holybadge.entities.Parishioner;

import java.time.LocalDateTime;

public class PartecipantPojo {

    // Questa classe contiene i dati con cui si identifica univocamente il parrocchiano e lo si può aggiungere all'incontro
    private Integer idMeeting;
    private Integer idParishioner;
    private String name;
    private String surname;
    private LocalDateTime birthDate;

    public PartecipantPojo(Parishioner parishioner){
        this.idParishioner = parishioner.getId();
        this.name = parishioner.getName();
        this.surname = parishioner.getSurname();
        this.birthDate = parishioner.getDataNascita();
    }


    public Integer getIdParishioner() {
        return idParishioner;
    }

    public void setIdParishioner(Integer idParishioner) {
        this.idParishioner = idParishioner;
    }

    public Integer getIdMeeting() {
        return idMeeting;
    }

    public void setIdMeeting(Integer idMeeting) {
        this.idMeeting = idMeeting;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

}
