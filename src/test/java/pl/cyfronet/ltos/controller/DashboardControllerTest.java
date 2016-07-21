package pl.cyfronet.ltos.controller;

import org.junit.Test;
import pl.cyfronet.ltos.repository.MockMvcSecurityTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by piotr on 12.07.16.
 */

public class DashboardControllerTest extends MockMvcSecurityTest {

    @Test
    public void testCustomFind() throws Exception {
        mockMvc.perform( get("/user/getSteps").session(user()) ).andExpect(status().isOk());
    }

}
