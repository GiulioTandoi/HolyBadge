package it.holyfamily.holybadge.pojos;

import java.time.LocalDateTime;

public class MeetingPojo {

    private LocalDateTime date;
    private String location;
    private String meetingName;

    public MeetingPojo() {
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

}
