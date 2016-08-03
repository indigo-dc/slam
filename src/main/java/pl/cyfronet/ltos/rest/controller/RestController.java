package pl.cyfronet.ltos.rest.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.cyfronet.ltos.repository.UserRepository;
import pl.cyfronet.ltos.rest.bean.IndigoWrapper;
import pl.cyfronet.ltos.rest.logic.IndigoRestLogic;

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

    @RequestMapping(value = "/preferences/{login}", method = RequestMethod.GET)
    public IndigoWrapper getUsers(@PathVariable String login) {
        return indigoRestLogic.getDataForLogin(login);
    }
}
