package it.holyfamily.holybadge.pojos;

import java.time.LocalDateTime;

public class PartecipationPojo {

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getMeetingLocation() {
        return meetingLocation;
    }

    public void setMeetingLocation(String meetingLocation) {
        this.meetingLocation = meetingLocation;
    }

    public LocalDateTime getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(LocalDateTime meetingDate) {
        this.meetingDate = meetingDate;
    }

    public LocalDateTime getPartecipation() {
        return partecipation;
    }

    public void setPartecipation(LocalDateTime partecipation) {
        this.partecipation = partecipation;
    }

    private String meetingName;
    private String meetingLocation;
    private LocalDateTime meetingDate;
    private LocalDateTime partecipation;


}
