package it.holyfamily.holybadge.database.repositories;

import it.holyfamily.holybadge.entities.ParishionerAdditionalInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "it.holyfamily.holybadge.database.repositories.ParishionerAdditionalInfoRepository")
public interface ParishionerAdditionalInfoRepository extends CrudRepository<ParishionerAdditionalInfo, Integer> {

    @Query(value = "SELECT pai.id,pai.infoname,pai.infovalue FROM parishioneradditionalinfo pai LEFT JOIN parishionertoadditional pta ON pai.id = pta.idInfo WHERE pta.idParishioner = :idParishioner", nativeQuery = true)
    List<ParishionerAdditionalInfo> getParishionerAdditionalInfo(int idParishioner);

}
