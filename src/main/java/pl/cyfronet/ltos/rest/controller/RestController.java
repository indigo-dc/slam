package pl.cyfronet.ltos.rest.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.cyfronet.ltos.repository.UserRepository;
import pl.cyfronet.ltos.rest.bean.IndygoWrapper;
import pl.cyfronet.ltos.rest.logic.IndygoRestLogic;

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
    IndygoRestLogic indygoRestLogic;

    @RequestMapping(value = "/preferences/{login}", method = RequestMethod.GET)
    public IndygoWrapper getUsers(@PathVariable String login) {

/*        IndygoWrapper result = new IndygoWrapper();

        Preferences preferences = Preferences.builder().
                priority(Arrays.asList(Priority.builder().service_id("serviceId").sla_id("sleId").weight(0.5)
                .build(),Priority.builder().service_id("serviceId2").sla_id("sleId2").weight(0.5)
                        .build())).build();
        Sla sla = Sla.builder().customer("indigo-dc")
                .id("dsafdsfsdfsdf78897sdf")
                .services(Arrays.asList(new Service("typ","serviceId",Arrays.asList(new Target("type","unit", ImmutableMap.<String, Object>builder().
                        put("numer", new Integer(1)).put("waga", new Float(0.5)).put("opis", new String("ale ma kota")).build()))))).build();

        result = IndygoWrapper.builder().sla(Arrays.asList(sla,sla)).preferences(preferences).build();*/

        return indygoRestLogic.getDataForLogin(login);
    }
}
