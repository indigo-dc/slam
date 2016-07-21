package pl.cyfronet.ltos.rest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import pl.cyfronet.ltos.repository.MockMvcSecurityTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by piotr on 18.07.16.
 */

public class RestControllerTest extends MockMvcSecurityTest {

    @Test
    public void testGetUsers() throws Exception {
        MvcResult result = mockMvc.perform( get("/rest/slam/preferences/test").session(user()) )
                .andExpect( status().isOk() ).andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertTrue( content.contains("\"customer\":\"test\"") );
    }

}
