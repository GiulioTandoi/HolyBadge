package it.holyfamily.holybadge.entities;

import javax.persistence.*;

@Entity
@Table(name = "parishionertoadditional")
public class ParishionerToAdditional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer idParishioner;
    private Integer idInfo;

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

    public int getIdInfo() {
        return idInfo;
    }

    public void setIdInfo(int idInfo) {
        this.idInfo = idInfo;
    }


}
