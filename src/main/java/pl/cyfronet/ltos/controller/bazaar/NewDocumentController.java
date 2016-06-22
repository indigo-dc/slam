package pl.cyfronet.ltos.controller.bazaar;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.cyfronet.ltos.bean.Role;
import pl.cyfronet.ltos.bean.Team;
import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.bean.legacy.CreateGrantData;
import pl.cyfronet.ltos.security.PortalUser;

import com.agreemount.Response;
import com.agreemount.bean.document.Document;
import com.agreemount.bean.identity.Identity;
import com.agreemount.bean.identity.TeamMember;
import com.agreemount.bean.identity.provider.IdentityProvider;
import com.agreemount.bean.response.ActionResponse;
import com.agreemount.bean.response.RedirectActionResponse;
import com.agreemount.logic.ActionLogic;
import com.agreemount.slaneg.action.ActionContext;
import com.agreemount.slaneg.fixtures.GenericYamlProvider;
import com.google.common.base.Preconditions;


@Controller
public class NewDocumentController {

    static Logger logger = LoggerFactory
            .getLogger(NewDocumentController.class);
    
    @Autowired
    private ActionLogic actionLogic;

    @Autowired
    private IdentityProvider identityProvider;

    
    @Autowired
    @Qualifier("identitiesYamlProvider")
    private GenericYamlProvider<Identity> identitiesYamlProvider;

    @Autowired
    @Qualifier("dummyLogin")
    private String login;


    public Identity getByLogin(String login) {
        return identitiesYamlProvider.getItems().stream()
                .collect(Collectors.toMap(Identity::getLogin, (a) -> a)).get(login);
    }      
    
    @RequestMapping(value = "grant/create", method = RequestMethod.PUT)
    @ResponseBody
    public Response<ActionResponse> getDocument(final CreateGrantData createGrantData) {
        
        PortalUser pu = (PortalUser) SecurityContextHolder.getContext().getAuthentication();
        User user = pu.getUserBean();
        Identity identity = getIdentity(user);
        
        Preconditions.checkNotNull(identity, "Identity [%s] was not found", login);

        /*
         * TODO maybe set identity elsewhere - when logging in
         */
        identityProvider.setIdentity(identity);

        Preconditions.checkNotNull(identityProvider.getIdentity(), "no user logged");

        logger.debug("" + identity);

        String grantOwnerTeam = createGrantData.getTeam();
        checkTeam(user, grantOwnerTeam);
      
        Document document = new Document();
        document.setName(createGrantData.getGrantId());
        document.setTeam(grantOwnerTeam);
        
        /*
         * check if user has this team...
         */

        ActionContext actionContext = actionLogic.runAction(document, "documentDraftFromController", "createNewRequest");

        document = actionContext.getDocument("newRoot");
        
        logger.debug("" + document);
        
        Response<ActionResponse> response = new Response<>();
        RedirectActionResponse redirectActionResponse = new RedirectActionResponse();
        redirectActionResponse.setRedirectToDocument(document.getId());
        response.setData(redirectActionResponse);

        return response;
    }

    private void checkTeam(User user, String grantTeam) {
        for (Team userTeam: user.getTeams()) {
           if (userTeam.getName().equals(grantTeam)) {
               return;
           }
        }
        // TODO throw proper exception to match http response
        throw new RuntimeException("Not allowed to choose team: " + grantTeam);
    }

    @Transactional
    public Identity getIdentity(User user) {
        Identity identity = new Identity();
        identity.setLogin(user.getId().toString());
        List<String> roles = user.getRoles().stream().map(entry -> entry.getName()).collect(Collectors.toList());
        List<Team> teams = user.getTeams();
        List<TeamMember> teamMembers = new LinkedList<TeamMember>();
        for (Team team : teams) {
            for (Role role: team.getRoles()) {
                TeamMember teamMember = new TeamMember(role.getName(), team.getName());
                teamMembers.add(teamMember);
            }
        }
        identity.setRoles(roles);
        identity.setTeamMembers(teamMembers);
        return identity;
    }      
    
    
}
