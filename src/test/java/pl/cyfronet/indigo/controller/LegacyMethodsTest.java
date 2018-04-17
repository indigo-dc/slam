package pl.cyfronet.indigo.controller;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;
import pl.cyfronet.indigo.repository.MockMvcSecurityTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

/**
 * Created by piotr on 12.07.16.
 */

public class LegacyMethodsTest extends MockMvcSecurityTest {

    @Test
    public void testLogout() throws Exception {
        MockHttpSession httpSession = user();
        Assert.assertFalse(httpSession.isInvalid());

        mockMvc.perform( get("/auth/logout").session(httpSession) ).andExpect(redirectedUrl("/"));
        Assert.assertTrue(httpSession.isInvalid());
    }

}
