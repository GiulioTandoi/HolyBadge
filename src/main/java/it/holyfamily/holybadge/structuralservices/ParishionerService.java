package it.holyfamily.holybadge.structuralservices;

import it.holyfamily.holybadge.database.repositories.InOutParishRepository;
import it.holyfamily.holybadge.entities.InOutParish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
public class ParishionerService {

    private static final Logger log = Logger.getLogger(ParishionerService.class.getName());

    @Autowired
    @Qualifier("it.holyfamily.holybadge.database.repositories.InOutParishRepository")
    private InOutParishRepository inOutParishRepository;

    public boolean registerEntrance (int idParishioner, LocalDateTime entranceTime){

        log.info("REGISTRAZIONE DI INGRESSO ALLE " + entranceTime + " PER L'ID " + idParishioner);
        try{
            InOutParish inOutParish = new InOutParish(entranceTime, idParishioner);
            inOutParishRepository.save(inOutParish);
        }catch (Exception ex){
            log.info("ERRORE DURANTE IL SALVATAGGIO DEL MOVIMENTO DI ENTRATA DEL PARISHIONER " + idParishioner);
            ex.printStackTrace();
            return false;
        }

        return true;
    }
    public boolean registeExit (int idParishioner, LocalDateTime exitTime){

        log.info("REGISTRAZIONE DI USCITA ALLE " + exitTime + " PER L'ID " + idParishioner);
        try {

            // Pageable è una classe di spring data che consente di selezionare un range di risultati come un LIMIT
            Pageable lastEntrance = Pageable.ofSize(1);
            InOutParish inOutParish = inOutParishRepository.findByIdParishionerOrderByEntranceTimeDesc(idParishioner, lastEntrance).get(0);
            inOutParish.setExitTime(exitTime);
            inOutParishRepository.save(inOutParish);

        }catch (Exception ex){
            log.info("ERRORE DURANTE IL SALVATAGGIO DEL MOVIMENTO DI USCITA DEL PARISHIONER " + idParishioner);
            ex.printStackTrace();
            return false;
        }

        return false;
    }

}
