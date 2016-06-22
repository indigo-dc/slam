package pl.cyfronet.ltos.repository.handler;

import java.util.Date;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import pl.cyfronet.ltos.bean.Affiliation;

/**
 * @author bwilk
 * 
 */
/*
 * It should be checked but it seems that these handlers work only in http
 * request context
 */
@RepositoryEventHandler(Affiliation.class)
public class AffiliationEventHandler {
    
    @HandleBeforeSave
    public void handleSave(Affiliation a) {
        a.setLastUpdateDate(new Date());
    }

    @HandleBeforeCreate
    public void handleCreate(Affiliation a) {
        a.setLastUpdateDate(new Date());
        a.setStatus("PENDING");
    }
    
}
