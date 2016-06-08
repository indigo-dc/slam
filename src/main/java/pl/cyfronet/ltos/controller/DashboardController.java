package pl.cyfronet.ltos.controller;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.cyfronet.ltos.bean.Affiliation;
import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.bean.UserAuth;
import pl.cyfronet.ltos.bean.legacy.GenericBean;
import pl.cyfronet.ltos.bean.legacy.Status;
import pl.cyfronet.ltos.bean.legacy.UserFirstSteps;
import pl.cyfronet.ltos.repository.UserRepository;
import pl.cyfronet.ltos.security.PortalUser;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class DashboardController {

    // TODO rewrite all legacy method to make them similar to repository REST controllers
    
    @Autowired
    UserRepository userRepo;

    @ResponseBody
    @RequestMapping(value = "user/getSteps")
    public String getSteps() throws IOException {
        GenericBean result = new GenericBean();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        getSteps(result);
        return mapper.writeValueAsString(result);
    }

    @Transactional
    private void getSteps(GenericBean result) {
        UserFirstSteps userFirstSteps = new UserFirstSteps();
        PortalUser pu = (PortalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserAuth userAuth = pu.getUserAuth();
        User user = userRepo.findOne(userAuth.getUser().getId());
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
        result.setType(userFirstSteps);
        result.setStatus(Status.SUCCESS);
    }
}
