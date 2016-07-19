package pl.cyfronet.ltos.controller;

import org.junit.Test;
import pl.cyfronet.ltos.repository.MockMvcSecurityTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by piotr on 13.07.16.
 */

public class IndexControllerTest extends MockMvcSecurityTest {

    @Test
    public void testHome() throws Exception {
        mockMvc.perform( get("/") ).andExpect(view().name("index"));
    }

    @Test
    public void testBazaar() throws Exception {
        mockMvc.perform( get("/bazaar").session(user()) ).andExpect(view().name("bazaar"));
    }

    @Test
    public void testIndygo() throws Exception {
        mockMvc.perform( get("/indygo").session(user()) ).andExpect(view().name("indygo"));
    }

}
