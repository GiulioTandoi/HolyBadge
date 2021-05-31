package it.holyfamily.holybadge.entities;

import com.sun.istack.Nullable;
import it.holyfamily.holybadge.pojos.ParishionerAdditionalInfoPojo;

import javax.persistence.*;

@Entity
@Table(name = "parishioneradditionalinfo")
public class ParishionerAdditionalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String infoName;
    @Nullable
    private String infoValue;

    public ParishionerAdditionalInfo() {
    }

    public ParishionerAdditionalInfo(ParishionerAdditionalInfoPojo parishionerAdditionalInfoPojo) {
        infoName = parishionerAdditionalInfoPojo.getInfoName();
        infoValue = parishionerAdditionalInfoPojo.getInfoValue();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
