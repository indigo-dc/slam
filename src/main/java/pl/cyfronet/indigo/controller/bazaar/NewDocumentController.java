package pl.cyfronet.indigo.controller.bazaar;

import com.agreemount.engine.facade.QueryFacade;
import com.agreemount.engine.facade.MetricFacade;
import com.agreemount.EngineFacade;
import com.agreemount.Response;
import com.agreemount.bean.document.Document;
import com.agreemount.bean.identity.Identity;
import com.agreemount.bean.response.ActionResponse;
import com.agreemount.bean.response.RedirectActionResponse;
import com.agreemount.slaneg.action.ActionContext;
import com.agreemount.slaneg.action.ActionContextFactory;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import pl.cyfronet.engine.rules.GenericYamlProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import pl.cyfronet.indigo.bean.Team;
import pl.cyfronet.indigo.bean.User;
import pl.cyfronet.indigo.bean.legacy.CreateGrantData;
import pl.cyfronet.indigo.security.PortalUser;

import java.util.stream.Collectors;


@Controller
public class NewDocumentController {

    static Logger logger = LoggerFactory
            .getLogger(NewDocumentController.class);

    @Value("${cmdb.url}")
    private String cmdbUrl;

    @Autowired
    private ActionContextFactory actionContextFactory;

    @Autowired
    private EngineFacade engineFacade;

    @Autowired
    private QueryFacade queryFacade;

    @Autowired
    private MetricFacade metricFacade;

    @Autowired
    @Qualifier("identitiesYamlProvider")
    private GenericYamlProvider<Identity> identitiesYamlProvider;

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

        ActionContext actionContext = actionContextFactory.createInstance(document);
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

    @RequestMapping(value = "api/sites", method = RequestMethod.GET)
    @ResponseBody
    public String getSites() throws UnirestException {
        return Unirest.get(cmdbUrl+"/cmdb/service/list").asString().getBody();
    }

    private void checkTeam(User user, String grantTeam) {
        for (Team userTeam : user.getTeams()) {
            if (userTeam.getName().equals(grantTeam)) {
                return;
            }
        }
        // TODO throw proper exception to match http response
        throw new RuntimeException("Not allowed to choose team: " + grantTeam);
    }
}
