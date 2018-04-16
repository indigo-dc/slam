package pl.cyfronet.indigo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.cyfronet.indigo.bean.Role;

/**
 * @author bwilk
 *
 */
@RepositoryRestResource
public interface RoleRepository extends CrudRepository<Role, Long> {

    /*
     * TODO secure this repository or remove it permanently 
     */
    Role findByName(@Param("name") String name);
}