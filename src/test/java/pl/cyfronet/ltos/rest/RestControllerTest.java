package pl.cyfronet.ltos.rest;

import com.agreemount.EngineFacade;
import com.agreemount.bean.document.Document;
import com.agreemount.bean.identity.Identity;
import com.agreemount.bean.identity.provider.IdentityProvider;
import com.agreemount.slaneg.action.ActionContext;
import com.agreemount.slaneg.action.ActionContextFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import pl.cyfronet.bazaar.engine.extension.bean.IndigoDocument;
import pl.cyfronet.ltos.repository.MockMvcSecurityTest;
import pl.cyfronet.ltos.rest.bean.sla.Sla;

import java.util.ArrayList;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by piotr on 18.07.16.
 */

public class RestControllerTest extends MockMvcSecurityTest {

    @Test
    public void testGetUsers() throws Exception {
//        MvcResult result = mockMvc.perform(get("/rest/slam/preferences/test").session(user()))
//                .andExpect(status().isOk()).andReturn();
//
//        String content = result.getResponse().getContentAsString();
//        Assert.assertTrue(content.contains("\"customer\":\"test\""));
    }

    @Autowired
    private ActionContextFactory actionContextFactory;

    @Autowired
    private EngineFacade engineFacade;

    @Autowired
    public IdentityProvider identityProvider;



    private void addSLA() throws Exception {

        Identity identity = new Identity();
        identity.setLogin("admin");

        ArrayList<String> roles = new ArrayList<String>() {{
            add("manager");
            add("admin");
        }};

        identity.setRoles(roles);

        identityProvider.setIdentity(identity);

        IndigoDocument document = new IndigoDocument();

        ActionContext actionContext = actionContextFactory.createInstance(document);

        actionContext.addDocument("documentDraftFromController", document);
        engineFacade.runAction(actionContext, "createNewComputingRequest");
    }

    private void addSLAEST() throws Exception {

//        HashMap<String, String> urlVariables = new HashMap<String, String>();
//        urlVariables.put("site", "BARI");
//        urlVariables.put("id", "test_document");
//        mockMvc.perform(put("/api/sla", urlVariables).session(user())).andExpect(status().isOk()).andReturn();

//        mockMvc.perform(put("/api/sla", urlVariables).session(user())).andExpect(status().isOk()).andReturn();


//        mockMvc.perform(get("/invoke?actionId=editPublicDragt-second-step-Buy+resources&documentId="+doc.getId()).session(user()))
//               .andExpect(status().isOk()).andReturn()
    }

//    @Test
//    public void testGetSLA() throws Exception {
        // add sla offer
//        MvcResult result = null;
//        addSLAEST();
//        result = mockMvc.perform(put("/pool/create", {id: "testPool", site: "VAL"}).session(user()))
//                .andExpect(status().isOk()).andReturn();
//
//        result = mockMvc.perform(get("/rest/slam/sla/"+sla.getId()).session(user()))
//                .andExpect(status().isOk()).andReturn();
//
//        String content = result.getResponse().getContentAsString();
//    }

    @Test
    public void testPreferences() {

    }

    @Test
    public void testGetSLAs() throws Exception {
        MvcResult result = mockMvc.perform(get("/rest/slam/sla").session(user()))
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
    }
}
