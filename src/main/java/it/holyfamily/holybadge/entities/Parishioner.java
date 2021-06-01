package it.holyfamily.holybadge.entities;

import com.sun.istack.Nullable;
import it.holyfamily.holybadge.pojos.ParishionerPojo;

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
    private String phoneNumber;

    @Nullable
    private String note;

    public Parishioner() {
    }

    public Parishioner(ParishionerPojo parishionerPojo) {
        name = parishionerPojo.getName();
        surname = parishionerPojo.getSurname();
        phoneNumber = parishionerPojo.getPhoneNumber();
        note = parishionerPojo.getNote();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
