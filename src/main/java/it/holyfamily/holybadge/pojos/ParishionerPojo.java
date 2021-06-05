package it.holyfamily.holybadge.pojos;

import java.time.LocalDateTime;

public class ParishionerPojo {

    private String name;
    private String surname;
    private String phoneNumber;
    private String secondPhone;
    private LocalDateTime dataNascita;
    private String allergiePatologie;
    private String tagliaMaglietta;

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

    public LocalDateTime getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDateTime dataNascita) {
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

}
