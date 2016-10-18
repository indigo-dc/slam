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
    public String getSites() {
        return  "{\"total_rows\":742,\"offset\":701,\"rows\":[\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758cf045\",\"key\":[\"service\"],\"value\":{\"sitename\":\"100IT\",\"provider_id\":\"provider-100IT\",\"hostname\":\"occi-api.100percentit.com\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758cfd60\",\"key\":[\"service\"],\"value\":{\"sitename\":\"RECAS-BARI\",\"provider_id\":\"provider-RECAS-BARI\",\"hostname\":\"cloud.recas.ba.infn.it\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758d0bb4\",\"key\":[\"service\"],\"value\":{\"sitename\":\"JINR-LCG2\",\"provider_id\":\"provider-JINR-LCG2\",\"hostname\":\"cloud.jinr.ru\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758d15a7\",\"key\":[\"service\"],\"value\":{\"sitename\":\"INDIGO-CATANIA-STACK\",\"provider_id\":\"provider-INDIGO-CATANIA-STACK\",\"hostname\":\"stack-server-02.ct.infn.it\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758d24b1\",\"key\":[\"service\"],\"value\":{\"sitename\":\"SCAI\",\"provider_id\":\"provider-SCAI\",\"hostname\":\"fc.scai.fraunhofer.de\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758d31c3\",\"key\":[\"service\"],\"value\":{\"sitename\":\"UA-BITP\",\"provider_id\":\"provider-UA-BITP\",\"hostname\":\"cloud-main.bitp.kiev.ua\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758d3ce4\",\"key\":[\"service\"],\"value\":{\"sitename\":\"IISAS-GPUCloud\",\"provider_id\":\"provider-IISAS-GPUCloud\",\"hostname\":\"nova3.ui.savba.sk\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758d3eb1\",\"key\":[\"service\"],\"value\":{\"sitename\":\"CSIC-EBD-LW\",\"provider_id\":\"provider-CSIC-EBD-LW\",\"hostname\":\"cloud.ebd.csic.es\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758d4d41\",\"key\":[\"service\"],\"value\":{\"sitename\":\"fedcloud.srce.hr\",\"provider_id\":\"provider-fedcloud.srce.hr\",\"hostname\":\"cloud.egi.cro-ngi.hr\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758d51b3\",\"key\":[\"service\"],\"value\":{\"sitename\":\"IN2P3-IRES\",\"provider_id\":\"provider-IN2P3-IRES\",\"hostname\":\"sbgcloud.in2p3.fr\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758d5d6a\",\"key\":[\"service\"],\"value\":{\"sitename\":\"KR-KISTI-CLOUD\",\"provider_id\":\"provider-KR-KISTI-CLOUD\",\"hostname\":\"fccont.kisti.re.kr\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758d6b41\",\"key\":[\"service\"],\"value\":{\"sitename\":\"NCG-INGRID-PT\",\"provider_id\":\"provider-NCG-INGRID-PT\",\"hostname\":\"aurora.ncg.ingrid.pt\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758d6e05\",\"key\":[\"service\"],\"value\":{\"sitename\":\"UPV-GRyCAP\",\"provider_id\":\"provider-UPV-GRyCAP\",\"hostname\":\"fc-one.i3m.upv.es\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758d74ae\",\"key\":[\"service\"],\"value\":{\"sitename\":\"CETA-GRID\",\"provider_id\":\"provider-CETA-GRID\",\"hostname\":\"controller.ceta-ciemat.es\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758d764e\",\"key\":[\"service\"],\"value\":{\"sitename\":\"BIFI\",\"provider_id\":\"provider-BIFI\",\"hostname\":\"server4-eupt.unizar.es\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758d8608\",\"key\":[\"service\"],\"value\":{\"sitename\":\"INFN-PADOVA-STACK\",\"provider_id\":\"provider-INFN-PADOVA-STACK\",\"hostname\":\"egi-cloud.pd.infn.it\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758d9561\",\"key\":[\"service\"],\"value\":{\"sitename\":\"MK-04-FINKICLOUD\",\"provider_id\":\"provider-MK-04-FINKICLOUD\",\"hostname\":\"occi.nebula.finki.ukim.mk\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758da12b\",\"key\":[\"service\"],\"value\":{\"sitename\":\"CYFRONET-CLOUD\",\"provider_id\":\"provider-CYFRONET-CLOUD\",\"hostname\":\"control.cloud.cyfronet.pl\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758daecb\",\"key\":[\"service\"],\"value\":{\"sitename\":\"TR-FC1-ULAKBIM\",\"provider_id\":\"provider-TR-FC1-ULAKBIM\",\"hostname\":\"fcctrl.ulakbim.gov.tr\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758db5b4\",\"key\":[\"service\"],\"value\":{\"sitename\":\"IISAS-FedCloud\",\"provider_id\":\"provider-IISAS-FedCloud\",\"hostname\":\"nova2.ui.savba.sk\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758dc256\",\"key\":[\"service\"],\"value\":{\"sitename\":\"INFN-CATANIA-NEBULA\",\"provider_id\":\"provider-INFN-CATANIA-NEBULA\",\"hostname\":\"nebula-server-01.ct.infn.it\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758dcb13\",\"key\":[\"service\"],\"value\":{\"sitename\":\"INFN-CATANIA-STACK\",\"provider_id\":\"provider-INFN-CATANIA-STACK\",\"hostname\":\"stack-server-01.ct.infn.it\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758dd0c5\",\"key\":[\"service\"],\"value\":{\"sitename\":\"BIFI\",\"provider_id\":\"provider-BIFI\",\"hostname\":\"server4-epsh.unizar.es\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758ddd02\",\"key\":[\"service\"],\"value\":{\"sitename\":\"PRISMA-INFN-BARI\",\"provider_id\":\"provider-PRISMA-INFN-BARI\",\"hostname\":\"prisma-cloud.ba.infn.it\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758de228\",\"key\":[\"service\"],\"value\":{\"sitename\":\"CESNET-MetaCloud\",\"provider_id\":\"provider-CESNET-MetaCloud\",\"hostname\":\"carach5.ics.muni.cz\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758ded52\",\"key\":[\"service\"],\"value\":{\"sitename\":\"CESGA\",\"provider_id\":\"provider-CESGA\",\"hostname\":\"cloud.cesga.es\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758dfb96\",\"key\":[\"service\"],\"value\":{\"sitename\":\"IFCA-LCG2\",\"provider_id\":\"provider-IFCA-LCG2\",\"hostname\":\"cloud.ifca.es\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758e08e6\",\"key\":[\"service\"],\"value\":{\"sitename\":\"HG-09-Okeanos-Cloud\",\"provider_id\":\"provider-HG-09-Okeanos-Cloud\",\"hostname\":\"okeanos-occi2.hellasgrid.gr\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758e1310\",\"key\":[\"service\"],\"value\":{\"sitename\":\"FZJ\",\"provider_id\":\"provider-FZJ\",\"hostname\":\"fsd-cloud.zam.kfa-juelich.de\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758e17a7\",\"key\":[\"service\"],\"value\":{\"sitename\":\"GoeGrid\",\"provider_id\":\"provider-GoeGrid\",\"hostname\":\"occi.cloud.gwdg.de\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758e1aea\",\"key\":[\"service\"],\"value\":{\"sitename\":\"HG-09-Okeanos-Cloud\",\"provider_id\":\"provider-HG-09-Okeanos-Cloud\",\"hostname\":\"okeanos-cdmi.hellasgrid.gr\",\"type\":\"storage\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758e1af9\",\"key\":[\"service\"],\"value\":{\"sitename\":\"PRISMA-INFN-BARI\",\"provider_id\":\"provider-PRISMA-INFN-BARI\",\"hostname\":\"prisma-swift.ba.infn.it\",\"type\":\"storage\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758e270b\",\"key\":[\"service\"],\"value\":{\"sitename\":\"BSC-Cloud\",\"provider_id\":\"provider-BSC-Cloud\",\"hostname\":\"bscgrid05.bsc.es\",\"type\":\"storage\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758e30eb\",\"key\":[\"service\"],\"value\":{\"sitename\":\"FZJ\",\"provider_id\":\"provider-FZJ\",\"hostname\":\"swift.zam.kfa-juelich.de\",\"type\":\"storage\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a025758e32f8\",\"key\":[\"service\"],\"value\":{\"sitename\":\"GoeGrid\",\"provider_id\":\"provider-GoeGrid\",\"hostname\":\"cdmi.cloud.gwdg.de\",\"type\":\"storage\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a02575e6f4bc\",\"key\":[\"service\"],\"value\":{\"provider_id\":\"provider-RECAS-BARI\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a02575e8040f\",\"key\":[\"service\"],\"value\":{\"provider_id\":\"provider-UPV-GRyCAP\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"4401ac5dc8cfbbb737b0a02575e81d9b\",\"key\":[\"service\"],\"value\":{\"provider_id\":\"provider-UPV-GRyCAP\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"7efc59c5db69ea67c5100de0f73981be\",\"key\":[\"service\"],\"value\":{\"provider_id\":\"provider-UPV-GRyCAP-beta\",\"type\":\"compute\"}},\n" +
                "{\"id\":\"7efc59c5db69ea67c5100de0f73ab567\",\"key\":[\"service\"],\"value\":{\"provider_id\":\"provider-RECAS-BARI\",\"type\":\"storage\"}},\n" +
                "{\"id\":\"7efc59c5db69ea67c5100de0f74f38b2\",\"key\":[\"service\"],\"value\":{\"provider_id\":\"provider-NCG-INGRID-PT\",\"type\":\"compute\"}}\n" +
                "]}";
    }

    @RequestMapping(value = "api/sla_action/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<DocumentAction> getActionSLA(@PathVariable("id") String id, HttpServletRequest request) {
        Document document = engineFacade.getDocument(id);
//        if(user)
//            throw new AccessDeniedException("User not authenticated");
//        ActionContext actionContext = actionContextFactory.createInstance(document);
        List<DocumentAction> ret = new ArrayList<>();
        if(request.isUserInRole("ROLE_ADMIN")){
            ret.add(new DocumentAction("reject", "Reject"));
            ret.add(new DocumentAction("accept", "Accept"));
        }
        else if(document.getAuthor().equals(((PortalUser)request.getUserPrincipal()).getId())) {
            ret.add(new DocumentAction("delete", "Delete"));
            ret.add(new DocumentAction("update", "Update"));
        }

//        ret.add(new DocumentAction("delete", "Delete"));
        return ret;
    }

    @RequestMapping(value = "api/sla_action/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void actionSLA(@PathVariable("id") String id, PortalUser user, @RequestParam("action") String action) {
        Document document = engineFacade.getDocument(id);
//        if(user)
//            throw new AccessDeniedException("User not authenticated");
        ActionContext actionContext = actionContextFactory.createInstance(document);

        if (action.equals("acceptRequest")) {
            engineFacade.runAction(actionContext, "acceptRequest");
        }
        else if (action.equals("rejectRequest")) {
            engineFacade.runAction(actionContext, "rejectRequest");
        }
//        document.
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
        Document document = engineFacade.getDocument(id);

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
    public Response<ActionResponse> createSLA(@RequestBody HashMap<String, String> slaData, PortalUser user) {

        IndigoDocument document = new IndigoDocument();
        document.setName(slaData.get("site"));
        document.setSite(slaData.get("name"));
//        document.setAuthor(user.get());

        ActionContext actionContext = actionContextFactory.createInstance(document);
        actionContext.addDocument("documentDraftFromController", document);
        engineFacade.runAction(actionContext, "createNewComputingRequest");

        document = (IndigoDocument) actionContext.getDocument("newRoot");

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
