package it.holyfamily.holybadge.entities;

import it.holyfamily.holybadge.pojos.GroupPojo;

import javax.persistence.*;

@Entity
@Table(name = "parishgroup")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    public Group() {
    }

    public Group(GroupPojo groupPojo) {
        name = groupPojo.getName();
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

    public void setName(String name) {
        this.name = name;
    }

}
