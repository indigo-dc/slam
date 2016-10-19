package pl.cyfronet.ltos.rest.controller;

import com.agreemount.bean.document.Document;
import com.agreemount.engine.facade.QueryFacade;
import com.agreemount.slaneg.action.ActionContext;
import com.agreemount.slaneg.action.ActionContextFactory;
import com.agreemount.slaneg.db.DocumentOperations;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.cyfronet.ltos.repository.UserRepository;
import pl.cyfronet.ltos.rest.bean.IndigoWrapper;
import pl.cyfronet.ltos.rest.bean.sla.Sla;
import pl.cyfronet.ltos.rest.logic.IndigoRestLogic;
import pl.cyfronet.ltos.security.PortalUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by km on 08.07.16.
 */

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest/slam")
@Log4j
public class RestController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    IndigoRestLogic indigoRestLogic;

    @Autowired
    QueryFacade queryFacade;

    @Autowired
    ActionContextFactory actionContextFactory;


    @Autowired
    private DocumentOperations documentOperations;

    @RequestMapping(value = "/preferences/{login}", method = RequestMethod.GET)
    public List<Document> getUser(@PathVariable String login, PortalUser user) {
//        String organisation = user.getUserBean().getOrganisationName();
//        Query query = new Query();
        List<Document> docs = queryFacade.getDocumentsForQuery("SignedSlaComp", actionContextFactory.createInstance());
//                documentOperations.getDocumentsWithQuery(query);
//        return indigoRestLogic.getDataForLogin(login);
        return docs;
    }

    @RequestMapping(value = "/preferences", method = RequestMethod.GET)
    public List<IndigoWrapper> getUsers(@RequestParam(required = false) String customer) {
        // TODO, fixme
        return new ArrayList<IndigoWrapper>();
    }

    @RequestMapping(value = "/sla/{id}", method = RequestMethod.GET)
    public Sla getSLA(@PathVariable String id) {
        return indigoRestLogic.getSLA(id);
    }

    @RequestMapping(value = "/sla", method = RequestMethod.GET)
    public List<Sla> getSLAs(@RequestParam(required = false) String customer, @RequestParam(required = false) String provider,
                             @RequestParam(required = false) String date, @RequestParam(required = false) String ids, @RequestParam(required = false) String service_type) {
        return indigoRestLogic.getSLAs(customer, provider, date, ids, service_type);
    }


}
