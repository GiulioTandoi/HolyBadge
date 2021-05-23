package it.holyfamily.holybadge.structuralservices;

import it.holyfamily.holybadge.database.repositories.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MeetingService {

    @Qualifier("it.holyfamily.holybadge.database.repositories.MeetingRepository")
    @Autowired
    MeetingRepository meetingRepository;

    // TODO getMeetingsList

    // TODO getMeetingPartecipants

}
