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
	@PreAuthorize("hasPermission(null, @authActs.activity('LIST_USERS'))")
	public Iterable<User> findAll();
//	
//	@Override
//	@PreAuthorize("hasPermission(#user, @authActs.activity('SAVE_USER'))")
//	public <S extends User> S save(S user);

//	@Override
//	@PreAuthorize("canSaveUser(#user)")
//	public <S extends User> S save(S user);
	
	@Override
	@PreAuthorize("checkPermissions(#user, @authActs.activity('SAVE_USER'))")
	public <S extends User> S save(S user);
	
	@Override
	@PreAuthorize("permitAll")
	@PostAuthorize("hasPermission(returnObject, @authActs.activity('VIEW_USER'))")
	public User findOne(Long id);
	
}