package it.holyfamily.holybadge.pojos;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;

public class AddParsToMeeting{

    public int getIdMeeting() {
        return idMeeting;
    }

    public void setIdMeeting(int idMeeting) {
        this.idMeeting = idMeeting;
    }

    public List<Integer> getIdParishioners() {
        return idParishioners;
    }

    public void setIdParishioners(List<Integer> idParishioners) {
        this.idParishioners = idParishioners;
    }

    private int idMeeting;
    private List<Integer> idParishioners;
}
