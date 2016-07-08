package pl.cyfronet.ltos.rest;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.cyfronet.ltos.repository.UserRepository;

/**
 * Created by km on 08.07.16.
 */

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest/slam")
@Log4j
public class RestController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/preferences/{login}", method = RequestMethod.GET)
    public UserRest getUsers(@PathVariable String login) {

        UserRest result = new UserRest();
        result.setName("Ala ma kota name");
        result.setCountry("Nibolandia");
        result.setEmail("Ala@ma.kota");
        result.setFullname(result.getName()+"="+login);

        return result;
    }
}
