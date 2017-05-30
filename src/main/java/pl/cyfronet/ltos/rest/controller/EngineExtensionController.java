package pl.cyfronet.ltos.rest.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.cyfronet.ltos.bean.DocumentWeight;
import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.repository.DocumentWeightRepository;
import pl.cyfronet.ltos.repository.UserRepository;
import pl.cyfronet.ltos.security.PortalUser;

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
