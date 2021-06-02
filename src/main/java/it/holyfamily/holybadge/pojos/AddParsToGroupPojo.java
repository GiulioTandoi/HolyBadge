package it.holyfamily.holybadge.pojos;

import java.util.List;

public class AddParsToGroupPojo {

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public List<Integer> getIdParishioners() {
        return idParishioners;
    }

    public void setIdParishioners(List<Integer> idParishioners) {
        this.idParishioners = idParishioners;
    }

    private int idGroup;
    private List<Integer> idParishioners;


}
