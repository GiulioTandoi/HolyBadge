package it.holyfamily.holybadge.entities;

import javax.persistence.*;

@Entity
@Table(name = "groups")
public class Groups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public Groups() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
