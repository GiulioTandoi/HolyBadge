package it.holyfamily.holybadge.pojos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ParishionerPojo {

    private String name;
    private String surname;
    private String phoneNumber;
    private String secondPhone;
    private LocalDate dataNascita;
    private String allergiePatologie;
    private String tagliaMaglietta;
    private List<Integer> memberships;
    private List<Integer> partecipations;

    private String note;

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String cognome) {
        this.surname = cognome;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public String getSecondPhone() {
        return secondPhone;
    }

    public void setSecondPhone(String secondPhone) {
        this.secondPhone = secondPhone;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getAllergiePatologie() {
        return allergiePatologie;
    }

    public void setAllergiePatologie(String allergiePatologie) {
        this.allergiePatologie = allergiePatologie;
    }

    public String getTagliaMaglietta() {
        return tagliaMaglietta;
    }

    public void setTagliaMaglietta(String tagliaMaglietta) {
        this.tagliaMaglietta = tagliaMaglietta;
    }

    public List<Integer> getMemberships() {
        return memberships;
    }

    public void setMemberships(List<Integer> memberships) {
        this.memberships = memberships;
    }

    public List<Integer> getPartecipations() {
        return partecipations;
    }

    public void setPartecipations(List<Integer> partecipations) {
        this.partecipations = partecipations;
    }

}
