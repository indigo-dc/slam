package pl.cyfronet.ltos.controller.bazaar;

import com.agreemount.EngineFacade;
import com.agreemount.Response;
import com.agreemount.bean.document.Document;
import com.agreemount.bean.identity.Identity;
import com.agreemount.bean.identity.provider.IdentityProvider;
import com.agreemount.bean.response.ActionResponse;
import com.agreemount.bean.response.RedirectActionResponse;
import com.agreemount.slaneg.action.ActionContext;
import com.agreemount.slaneg.action.ActionContextFactory;
import com.agreemount.slaneg.fixtures.GenericYamlProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.cyfronet.ltos.bean.Team;
import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.bean.legacy.CreateGrantData;
import pl.cyfronet.ltos.security.PortalUser;

import java.util.stream.Collectors;


@Controller
public class NewDocumentController {

    static Logger logger = LoggerFactory
            .getLogger(NewDocumentController.class);

    @Autowired
    private ActionContextFactory actionContextFactory;

    @Autowired
    private EngineFacade engineFacade;

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

        String grantOwnerTeam = createGrantData.getTeam();
        checkTeam(user, grantOwnerTeam);
      
        Document document = new Document();
        document.setName(createGrantData.getGrantId());
        document.setTeam(grantOwnerTeam);
        
        /*
         * check if user has this team...
         */

        ActionContext actionContext =  actionContextFactory.createInstance(document);
        actionContext.addDocument("documentDraftFromController", document);
        engineFacade.runAction(actionContext, "createNewRequest");

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
}
