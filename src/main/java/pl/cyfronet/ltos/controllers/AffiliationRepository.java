package pl.cyfronet.ltos.controllers;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import pl.cyfronet.ltos.bean.Affiliation;

@RepositoryRestResource
@PreAuthorize("denyAll")
public interface AffiliationRepository extends CrudRepository<Affiliation, Long> {
	
	@Override
	@PreAuthorize("checkPolicy(@activities.get('LIST'))")
	public Iterable<Affiliation> findAll();
	
}