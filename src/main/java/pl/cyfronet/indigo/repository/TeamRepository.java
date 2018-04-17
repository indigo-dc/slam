package pl.cyfronet.indigo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import pl.cyfronet.indigo.bean.Team;

/**
 * @author bwilk
 *
 */
@RepositoryRestResource
public interface TeamRepository extends CrudRepository<Team, Long> {

}