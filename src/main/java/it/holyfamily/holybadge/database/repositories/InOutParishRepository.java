package it.holyfamily.holybadge.database.repositories;

import it.holyfamily.holybadge.entities.InOutParish;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "it.holyfamily.holybadge.database.repositories.InOutParishRepository")
public interface InOutParishRepository extends CrudRepository <InOutParish, Integer> {

    List<InOutParish> findByIdParishionerOrderByEntranceTimeDesc(@Param("idParishioner") int idParishioner, Pageable pageable);

    List<InOutParish> findAllByOrderByEntranceTimeDesc(Pageable pageable);

}
