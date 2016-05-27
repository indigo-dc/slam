package pl.cyfronet.ltos.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import pl.cyfronet.ltos.bean.Affiliation;

@RepositoryRestResource
@PreAuthorize("hasRole('ADMIN')")
public interface AffiliationRepository extends
		CrudRepository<Affiliation, Long> {

	@Override
	@PreAuthorize("checkPolicy(@activities.get('LIST_AFFILIATIONS'))")
	@PostFilter("checkPolicyAffiliation(filterObject, @activities.get('LIST_AFFILIATIONS'))")
	public Iterable<Affiliation> findAll();

	@Override
	@PreAuthorize("checkPolicyAffiliation(#affiliation, @activities.get('SAVE_AFFILIATION'))")
	public <S extends Affiliation> S save(S affiliation);

	@Override
	@PreAuthorize("permitAll")
	@PostAuthorize("checkPolicyAffiliation(returnObject, @activities.get('VIEW_AFFILIATION'))")
	public Affiliation findOne(Long id);

}