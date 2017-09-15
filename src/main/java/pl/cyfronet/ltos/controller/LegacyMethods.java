package pl.cyfronet.ltos.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;
import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.security.UserInfo;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@Log4j
public class LegacyMethods {

    @RequestMapping(value = "user/get", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<User> getUser() throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (user == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "identity/get", method = RequestMethod.GET)
    public ResponseEntity<UserInfo> getIndentityOld() throws IOException {
        UserInfo user = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
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
