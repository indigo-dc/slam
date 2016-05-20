package pl.cyfronet.ltos.controllers;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import pl.cyfronet.ltos.bean.User;

@RepositoryRestResource
@PreAuthorize("denyAll")
public interface UserRepository extends CrudRepository<User, Long> {
	
	@Override
	@PreAuthorize("hasPermission(null, @activities.get('LIST_USERS'))")
	public Iterable<User> findAll();
	
	@Override
	@PreAuthorize("checkPolicy(#user, @activities.get('SAVE_USER'))")
	public <S extends User> S save(S user);
	
	@Override
	@PreAuthorize("permitAll")
	@PostAuthorize("checkPolicy(returnObject, @activities.get('VIEW_USER'))")
	public User findOne(Long id);
	
}