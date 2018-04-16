package pl.cyfronet.indigo.rest.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.cyfronet.indigo.bean.DocumentWeight;
import pl.cyfronet.indigo.bean.User;
import pl.cyfronet.indigo.repository.DocumentWeightRepository;
import pl.cyfronet.indigo.repository.UserRepository;
import pl.cyfronet.indigo.security.PortalUser;

import java.util.List;

/**
 * Created by km on 08.07.16.
 */

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/")
@Log4j
public class EngineExtensionController {

    @Autowired
    private DocumentWeightRepository documentWeightRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/document-weights", method = RequestMethod.GET)
    public List<DocumentWeight> getDocumentWeights(PortalUser user) {
        return user.getUserBean().getDocuments();
    }

    @RequestMapping(value = "/document-weights", method = RequestMethod.POST)
    @Transactional
    public void setDocumentWeights(PortalUser pUser, @RequestBody List<DocumentWeight> documentWeights) {
        User user = userRepository.findOne(pUser.getUserBean().getId());

        for(DocumentWeight documentWeight : documentWeights) {
            documentWeight.setUser(user);
            documentWeightRepository.save(documentWeight);
        }
    }

}
