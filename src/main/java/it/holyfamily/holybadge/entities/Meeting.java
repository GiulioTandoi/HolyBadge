package it.holyfamily.holybadge.entities;

import com.sun.istack.Nullable;
import it.holyfamily.holybadge.pojos.MeetingPojo;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "meeting")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime date;
    private String location;
    private String meetingName;

    public Meeting() {
    }

    public Meeting(MeetingPojo meetingPojo) {
        meetingName = meetingPojo.getMeetingName();
        location = meetingPojo.getLocation();
        date = meetingPojo.getDate();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
