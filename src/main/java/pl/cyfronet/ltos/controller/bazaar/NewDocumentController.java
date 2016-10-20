package pl.cyfronet.ltos.controller.bazaar;

import com.agreemount.bean.metric.Metric;
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
import com.agreemount.slaneg.action.definition.Action;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import pl.cyfronet.bazaar.engine.rules.GenericYamlProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import pl.cyfronet.bazaar.engine.extension.bean.IndigoDocument;
import pl.cyfronet.ltos.bean.Team;
import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.bean.legacy.CreateGrantData;
import pl.cyfronet.ltos.rest.bean.sla.DocumentAction;
import pl.cyfronet.ltos.security.PortalUser;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @RequestMapping(value = "api/sla_action/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Action> getActionSLA(@PathVariable("id") String id, HttpServletRequest request) {
        Document document = engineFacade.getDocument(id);
        List<Action> ret = engineFacade.getActionsAvailableForDocument(document);
//        if(user)
//            throw new AccessDeniedException("User not authenticated");
//        ActionContext actionContext = actionContextFactory.createInstance(document);
        //List<DocumentAction> ret = new ArrayList<>();
//        if(request.isUserInRole("ROLE_ADMIN")){
//            ret.add(new DocumentAction("reject", "Reject"));
//            ret.add(new DocumentAction("accept", "Accept"));
//        }
//        else if(document.getAuthor().equals(((PortalUser)request.getUserPrincipal()).getId())) {
//            ret.add(new DocumentAction("delete", "Delete"));
//            ret.add(new DocumentAction("update", "Update"));
//        }

//        ret.add(new DocumentAction("delete", "Delete"));
        return ret;
    }

    @RequestMapping(value = "api/sla_action/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Response<ActionResponse>  actionSLA(@PathVariable("id") String id, @RequestBody pl.cyfronet.ltos.rest.bean.sla.Document slaData,
                          PortalUser user, @RequestParam("action") String action) {
        IndigoDocument document = (IndigoDocument)engineFacade.getDocument(id);

        IndigoDocument formDocument = new IndigoDocument();
        formDocument.setMetrics(slaData.getMetrics());

        ActionContext actionContext = actionContextFactory.createInstance(document);

        actionContext.addDocument("FORM",formDocument);

        ActionContext actionContextResponse = engineFacade.runAction(actionContext, action);


        Response<ActionResponse> response = new Response<>();
        RedirectActionResponse redirectActionResponse = new RedirectActionResponse();
        redirectActionResponse.setRedirectToDocument(actionContextResponse.getRedirectToAlias());
        response.setData(redirectActionResponse);

        return response;
    }

    @RequestMapping(value = "api/sla/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteSLA(@PathVariable("id") String id, PortalUser principal) {
        IndigoDocument document = (IndigoDocument)engineFacade.getDocument(id);
        if(!document.getAuthor().equals(principal.getName()))
            throw new AccessDeniedException("User not authenticated");

//        document.
    }

    @RequestMapping(value = "api/sla/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Response<ActionResponse> editSLA(@PathVariable("id") String id, @RequestBody HashMap<String, String> slaData, PortalUser user) {
        IndigoDocument document = (IndigoDocument)engineFacade.getDocument(id);

        if(!document.getAuthor().equals(user.getId()))
            throw new AccessDeniedException("User not authenticated");

        document.setName(slaData.get("site"));
//        document.setSite(slaData.get("name"));
        document.setAuthor((String)user.getCredentials());

        Map<String, Object> metrics = document.getMetrics();

        for (Map.Entry<String, String > entry : slaData.entrySet()) {
            if(metrics.containsKey(entry.getKey())){
                metrics.replace(entry.getKey(), entry.getValue());
            }
        }

        document.setMetrics(metrics);

        Response<ActionResponse> response = new Response<>();
        RedirectActionResponse redirectActionResponse = new RedirectActionResponse();
        redirectActionResponse.setRedirectToDocument(document.getId());
        response.setData(redirectActionResponse);

        return response;
    }

    @RequestMapping(value = "api/sla", method = RequestMethod.POST)
    @ResponseBody
    public Response<ActionResponse> createSLA(@RequestBody pl.cyfronet.ltos.rest.bean.sla.Document slaData, PortalUser user) {

        IndigoDocument document = new IndigoDocument();
        document.setName(slaData.getName());
        document.setSite(slaData.getSite());
        document.setSiteName(slaData.getSiteName());
        document.setMetrics(slaData.getMetrics());

        ActionContext actionContext = actionContextFactory.createInstance(document);
        actionContext.addDocument("documentDraftFromController", document);
        engineFacade.runAction(actionContext, "createNewComputingRequest");

        document = (IndigoDocument) actionContext.getDocument("newRoot");

        Response<ActionResponse> response = new Response<>();
        RedirectActionResponse redirectActionResponse = new RedirectActionResponse();
        redirectActionResponse.setRedirectToDocument(document.getId());
        response.setData(redirectActionResponse);

        return response;
    }


    @RequestMapping(value = "api/metrics", method = RequestMethod.GET)
    @ResponseBody
    public List<Metric> getMetrics(final String type) {
        List<Metric> metrics = metricFacade.fetchMetricsByCategoryId("computingTimeRestrictions");
        metrics.addAll(metricFacade.fetchMetricsByCategoryId("publicIPRestrictions"));
        return metrics;
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
