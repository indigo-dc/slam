package pl.cyfronet.ltos.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.cyfronet.ltos.bean.Affiliation;
import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.bean.legacy.UserFirstSteps;
import pl.cyfronet.ltos.repository.UserRepository;
import pl.cyfronet.ltos.security.PortalUser;

@Controller
public class DashboardController {

    // TODO rewrite all legacy method to make them similar to repository REST controllers
    
    @Autowired
    UserRepository userRepo;
    
    @RequestMapping(value="user/getSteps", method=RequestMethod.GET)
    public ResponseEntity<UserFirstSteps> customFind() {
        UserFirstSteps steps = steps();
        return new ResponseEntity<UserFirstSteps>(steps, HttpStatus.OK);
    }

    @Transactional
    private UserFirstSteps steps() {
        UserFirstSteps userFirstSteps = new UserFirstSteps();
        PortalUser pu = (PortalUser) SecurityContextHolder.getContext().getAuthentication();
        User user = pu.getUserBean();
        List<Affiliation> affiliations = user.getAffiliations();
        boolean hasAffiliation = false;
        if (affiliations != null) {
            hasAffiliation = affiliations.stream().anyMatch(
                    aff -> {
                        return aff.getStatus() != null
                                && aff.getStatus().equalsIgnoreCase("ACTIVE");
                    });
        }
        userFirstSteps.setHasAffiliation(hasAffiliation);
        userFirstSteps.setHasResource(false);
        userFirstSteps.setHasScienceGateway(false);
        return userFirstSteps;
    }
}
