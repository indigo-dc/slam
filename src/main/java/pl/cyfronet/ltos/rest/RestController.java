package pl.cyfronet.ltos.rest;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.cyfronet.ltos.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
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

    @RequestMapping(value = "/preferences/{login}", method = RequestMethod.GET)
    public List<UserRest> getUsers(@PathVariable String login) {

        List <UserRest> resultList = new ArrayList<>();

        UserRest result = new UserRest();
        result.setName("Ala ma kota name");
        result.setCountry("Nibolandia");
        result.setEmail("Ala@ma.kota");
        result.setFullname(result.getName()+"="+login);
        result.setAtributes(Arrays.asList("one","second","omega"));

        UserRest result2 = new UserRest();
        result2.setName("Ala ma kota name2");
        result2.setCountry("Nibolandia2");
        result2.setEmail("Ala@ma.kota2");
        result2.setFullname(result.getName()+"2="+login);
        result2.setAtributes(Arrays.asList("one2","second2","omega2"));

        resultList.add(result);
        resultList.add(result2);
        return resultList;
    }
}
