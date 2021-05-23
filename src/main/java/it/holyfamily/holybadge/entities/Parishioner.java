package it.holyfamily.holybadge.entities;

import com.sun.istack.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "parishioner")
public class Parishioner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String surname;
    @Nullable
    private Integer phoneNumber;

    @Nullable
    private String note;

    public Parishioner() {
    }

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

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
