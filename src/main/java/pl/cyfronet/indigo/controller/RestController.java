package pl.cyfronet.indigo.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.cyfronet.indigo.repository.UserRepository;
import pl.cyfronet.indigo.rest.bean.IndigoWrapper;
import pl.cyfronet.indigo.rest.bean.sla.Sla;
import pl.cyfronet.indigo.rest.logic.IndigoRestLogic;

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

    @RequestMapping(value = "/preferences/{login}", method = RequestMethod.GET)
    public IndigoWrapper getUser(@PathVariable String login) {
        return indigoRestLogic.getDataForLogin(login);
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
