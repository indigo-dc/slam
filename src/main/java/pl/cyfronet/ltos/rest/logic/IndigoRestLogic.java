package pl.cyfronet.ltos.rest.logic;

import com.agreemount.bean.document.Document;
import com.agreemount.engine.facade.QueryFacade;
import com.agreemount.slaneg.action.ActionContextFactory;
import com.agreemount.slaneg.db.DocumentOperations;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.repository.UserRepository;
import pl.cyfronet.ltos.rest.bean.IndigoWrapper;
import pl.cyfronet.ltos.rest.bean.sla.Sla;
import pl.cyfronet.ltos.rest.util.IndigoConverter;

import java.util.List;

/**
 * Created by km on 11.07.16.
 */
@Component
@Log4j
public class IndigoRestLogic {

    @Autowired
    private DocumentOperations documentOperations;

    @Autowired
    private IndigoConverter converter;

    @Autowired
    ActionContextFactory actionContextFactory;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    QueryFacade queryFacade;

    public IndigoWrapper getDataForLogin(String login) {
        if(userRepository.findByOrganisationName(login).size() == 0) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        List<Document> docs = queryFacade.getDocumentsForQuery("SignedSlaComp", actionContextFactory.createInstance());
        return converter.convertSlasListForRestApi(docs, login);
    }

    public Sla getSLA(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        List<Document> result = documentOperations.getDocumentsWithQuery(query);
        return converter.prepareSlaList(result, null).get(0);
    }

    public List<Sla> getSLAs(String customer, String provider, String date, String ids, String service_type) {
        Query query = new Query();
//        query.addCriteria(Criteria.where("id").is(id));
        List<Document> result = documentOperations.getDocumentsWithQuery(query);
        return converter.prepareSlaList(result, null);
    }

    private List<Document> getDocuments(String login) {
        Query query = new Query();
        query.addCriteria(Criteria.where("author").is(login));
        List<Document> result = documentOperations.getDocumentsWithQuery(query);
        return result;
    }
}
