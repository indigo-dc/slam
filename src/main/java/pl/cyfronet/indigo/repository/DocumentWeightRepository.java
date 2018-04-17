package pl.cyfronet.indigo.repository;

import org.springframework.data.repository.CrudRepository;
import pl.cyfronet.indigo.bean.DocumentWeight;
import pl.cyfronet.indigo.bean.DocumentWeightPk;

/**
 * Created by mszostak on 12.04.17.
 */
public interface DocumentWeightRepository extends CrudRepository<DocumentWeight, DocumentWeightPk> {
}
