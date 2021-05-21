package it.holyfamily.holybadge.entities;

import javax.persistence.*;

@Entity
@Table(name = "parishioner")
public class Parishioner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String surname;
    private int phoneNumber;

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

}
