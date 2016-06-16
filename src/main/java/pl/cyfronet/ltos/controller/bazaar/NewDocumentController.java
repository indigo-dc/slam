package pl.cyfronet.ltos.controller.bazaar;

import com.agreemount.bean.document.Document;
import com.agreemount.bean.identity.Identity;
import com.agreemount.bean.identity.provider.IdentityProvider;
import com.agreemount.logic.ActionLogic;
import com.agreemount.slaneg.action.ActionContext;
import com.agreemount.slaneg.fixtures.GenericYamlProvider;
import com.google.common.base.Preconditions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.cyfronet.ltos.bean.User;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by chomik on 5/23/16.
 */
@Controller
public class NewDocumentController {

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
    
    @RequestMapping("create-new-request")
    public String createNewRequest() {
        
        Identity identity = getByLogin(login);
        Preconditions.checkNotNull(identity, "Identity [%s] was not found", login);

        identityProvider.setIdentity(identity);

        Preconditions.checkNotNull(identityProvider.getIdentity(), "no user logged");

        Document document = new Document();
        document.setName(identityProvider.getIdentity().getLogin() + ": " + UUID.randomUUID().toString());
        document.setTeam(identityProvider.getIdentity().getTeamMembers().get(0).getTeam());

        ActionContext actionContext = actionLogic.runAction(document, "documentDraftFromController", "createNewRequest");

        document = actionContext.getDocument("newRoot");

        return "redirect:bazaar#/document/" + document.getId();
    }
    
}
