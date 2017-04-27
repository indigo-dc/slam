package pl.cyfronet.ltos.repository;

import org.springframework.data.repository.CrudRepository;
import pl.cyfronet.ltos.bean.DocumentWeight;
import pl.cyfronet.ltos.bean.DocumentWeightPk;

/**
 * Created by mszostak on 12.04.17.
 */
public interface DocumentWeightRepository extends CrudRepository<DocumentWeight, DocumentWeightPk> {
}
