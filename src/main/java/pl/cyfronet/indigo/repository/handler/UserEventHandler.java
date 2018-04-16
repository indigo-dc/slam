package pl.cyfronet.indigo.repository.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import pl.cyfronet.indigo.bean.Role;
import pl.cyfronet.indigo.bean.User;
import pl.cyfronet.indigo.repository.RoleRepository;

import java.util.Arrays;

/**
 * @author bwilk
 * 
 */
/*
 * It should be checked but it seems that these handlers work only in httpw
 * request context
 */
@RepositoryEventHandler(User.class)
public class UserEventHandler {

    private Logger logger = LoggerFactory.getLogger(UserEventHandler.class);

    @Autowired
    private RoleRepository roleRepository;

    @HandleBeforeSave
    public void handleUserSave(User p) {
        logger.info("USER being saved: " + p);
    }

    @HandleBeforeCreate
    public void handleUserCreate(User p) {
        Role role = roleRepository.findByName("manager");
        p.setRoles(Arrays.asList(role));
        logger.error("USER being created: " + p);
    }

}
