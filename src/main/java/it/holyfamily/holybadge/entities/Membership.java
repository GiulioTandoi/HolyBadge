package it.holyfamily.holybadge.entities;

import javax.persistence.*;

@Entity
@Table(name = "membership")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer idParishioner;
    private Integer idGroup;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdParishioner() {
        return idParishioner;
    }

    public void setIdParishioner(Integer idParishioner) {
        this.idParishioner = idParishioner;
    }

    public Integer getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Integer idGroup) {
        this.idGroup = idGroup;
    }

}
