package pl.cyfronet.ltos.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
import pl.cyfronet.ltos.security.SimpleUser;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class LegacyMethods {

	@Autowired
	private UserRepository users;
	
    @ResponseBody
    @RequestMapping(value = "user/get")
    @Transactional
    public String getUser() throws IOException {
    	GenericBean<User> result = new GenericBean<User>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);        
        
        PortalUser pu = (PortalUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
        User user = users.findOne(pu.getId());
        result.setType(user);
        result.setStatus(Status.SUCCESS);
        return mapper.writeValueAsString(result);
    }
	
    @ResponseBody
    @Transactional
    @RequestMapping(value = "identity/get")
    public String getIndentity() throws IOException {    	
    	GenericBean<User> result = new GenericBean<User>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        PortalUser pu = (PortalUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
    	User user = users.findOne(pu.getId());
        result.setType(user);
        result.setStatus(Status.SUCCESS);
        return mapper.writeValueAsString(result);
    }
   
    @RequestMapping(value = "/auth/logout", method = RequestMethod.GET)
    public RedirectView logout(HttpSession session)  {
        session.invalidate();
        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        redirectView.setUrl("/");
        return redirectView;
    }
    
}
