package it.holyfamily.holybadge.pojos;

import it.holyfamily.holybadge.entities.Parishioner;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

public class PartecipantPojo {

    // Questa classe contiene i dati con cui si identifica univocamente il parrocchiano e lo si può aggiungere all'incontro
    private Integer idMeeting;
    private Parishioner parishioner;
    private LocalDateTime partecipation;

    public PartecipantPojo(Parishioner parishioner, int idMeeting, LocalDateTime partecipation){
        this.parishioner = parishioner;
        this.idMeeting = idMeeting;
        this.partecipation = partecipation;
    }

    public Integer getIdMeeting() {
        return idMeeting;
    }

    public void setIdMeeting(Integer idMeeting) {
        this.idMeeting = idMeeting;
    }

    public Parishioner getParishioner() {
        return parishioner;
    }

    public void setParishioner(Parishioner parishioner) {
        this.parishioner = parishioner;
    }

    public LocalDateTime getPartecipation() {
        return partecipation;
    }

    public void setPartecipation(LocalDateTime partecipation) {
        this.partecipation = partecipation;
    }


}
