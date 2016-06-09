package pl.cyfronet.ltos.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.bean.legacy.GenericBean;
import pl.cyfronet.ltos.bean.legacy.Status;
import pl.cyfronet.ltos.repository.UserRepository;
import pl.cyfronet.ltos.security.PortalUser;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class LegacyMethods {

    @Autowired
    private UserRepository users;

    @RequestMapping(value = "user/get", method = RequestMethod.GET)
    @Transactional
    public RedirectView getUser() throws IOException {
        PortalUser pu = (PortalUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        redirectView.setUrl("/users/" + pu.getId());
        return redirectView;
    }

    @Transactional
    @RequestMapping(value = "identity/get", method = RequestMethod.GET)
    public ResponseEntity<User> getIndentity() throws IOException {
        PortalUser pu = (PortalUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        /*
         * Implement identity
         */
        User user = null;
        try {
             user = pu.getUserAuth().getUser();
        } catch (Exception e) {
            return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/auth/logout", method = RequestMethod.GET)
    public RedirectView logout(HttpSession session) {
        session.invalidate();
        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        redirectView.setUrl("/");
        return redirectView;
    }

}
