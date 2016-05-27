package pl.cyfronet.ltos.repository.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import pl.cyfronet.ltos.bean.User;

@Component
@RepositoryEventHandler(User.class)
public class UserEventHandler {

	private Logger logger = LoggerFactory.getLogger(UserEventHandler.class);

	@HandleBeforeSave
	public void handleUserSave(User p) {
		logger.info("USER being saved: " + p);
	}
	
	@HandleBeforeCreate
	public void handleUserCreate(User p) {
		logger.info("USER being created: " + p);
	}

}
