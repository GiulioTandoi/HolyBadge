package it.holyfamily.holybadge.pojos;

import it.holyfamily.holybadge.entities.Group;

public class MembershipPojo {


    private Integer id;
    private String name;
    private boolean membership;

    public MembershipPojo (Group group, boolean membership){
        this.id = group.getId();
        this.name = group.getName();
        this.membership = membership;
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

    public boolean isMembership() {
        return membership;
    }

    public void setMembership(boolean membership) {
        this.membership = membership;
    }


}
