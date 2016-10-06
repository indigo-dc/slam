package pl.cyfronet.ltos.rest;

import com.agreemount.bean.document.Document;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import pl.cyfronet.bazaar.engine.extension.bean.IndigoDocument;
import pl.cyfronet.ltos.repository.MockMvcSecurityTest;
import pl.cyfronet.ltos.rest.bean.sla.Sla;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by piotr on 18.07.16.
 */

public class RestControllerTest extends MockMvcSecurityTest {

    @Test
    public void testGetUsers() throws Exception {
        MvcResult result = mockMvc.perform(get("/rest/slam/preferences/test").session(user()))
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertTrue(content.contains("\"customer\":\"test\""));
    }

    @Test
    public void testGetSLAs() throws Exception {
        MvcResult result = mockMvc.perform(get("/rest/slam/sla").session(user()))
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
    }

    private void addSLA() {
//        mockMvc.perform(get("/invoke?actionId=editPublicDragt-second-step-Buy+resources&documentId="+doc.getId()).session(user()))
//                .andExpect(status().isOk()).andReturn()
    }

    @Test
    public void testGetSLA() throws Exception {
        // add sla offer
        MvcResult result = null;

//        result = mockMvc.perform(put("/pool/create", {grantId: "testPool", site: "VAL"}).session(user()))
//                .andExpect(status().isOk()).andReturn();
//
//        result = mockMvc.perform(get("/rest/slam/sla/"+sla.getId()).session(user()))
//                .andExpect(status().isOk()).andReturn();
//
//        String content = result.getResponse().getContentAsString();
    }

}
