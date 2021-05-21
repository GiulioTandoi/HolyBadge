package it.holyfamily.holybadge.entities;

import javax.persistence.*;

@Entity
@Table(name = "parishionertoadditional")
public class ParishionerToAdditional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int idParishioner;
    private int idInfo;

    public int getIdParishioner() {
        return idParishioner;
    }

    public void setIdParishioner(int idParishioner) {
        this.idParishioner = idParishioner;
    }

    public int getIdInfo() {
        return idInfo;
    }

    public void setIdInfo(int id_info) {
        this.idInfo = idInfo;
    }


}
