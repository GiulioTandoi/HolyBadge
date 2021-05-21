package it.holyfamily.holybadge.entities;

import javax.persistence.*;

@Entity
@Table(name = "parishionerAdditionInfo")
public class ParishionerAdditionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String infoName;
    private String infoValue;

    public ParishionerAdditionInfo() {
    }

    public String getInfoName() {
        return infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    public String getInfoValue() {
        return infoValue;
    }

    public void setInfoValue(String infoValue) {
        this.infoValue = infoValue;
    }

}
