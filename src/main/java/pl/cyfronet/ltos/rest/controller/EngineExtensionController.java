package pl.cyfronet.ltos.rest.controller;

import com.agreemount.slaneg.db.DocumentOperations;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.cyfronet.ltos.bean.DocumentWeight;
import pl.cyfronet.ltos.bean.User;
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
@RequestMapping("/")
@Log4j
public class EngineExtensionController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/document-weights", method = RequestMethod.GET)
    public List<DocumentWeight> getDocumentWeights(PortalUser user) {
        return user.getUserBean().getDocuments();
    }

}
