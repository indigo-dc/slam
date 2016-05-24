package pl.cyfronet.ltos.repository;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pl.cyfronet.ltos.ApplicationTest;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = ApplicationTest.class)
public class ResourceAuthorizationTests {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
        		.webAppContextSetup(this.wac)
        		.apply(springSecurity())
        		.build();
    }
    
    @Test
    @WithMockUser(username = "anonymous", roles={})
    public void getUsersAnonymous() throws Exception {
        this.mockMvc.perform(get("/users")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden());
    }   
    
    @Test
    @WithMockUser(roles={"ADMIN", "USER"})
    public void getUsersAdmin() throws Exception {
        this.mockMvc.perform(get("/users")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"));
    }
    
    @Test
    @WithMockUser(username = "anonymous", roles={})
    public void getAffiliationsAnonymous() throws Exception {		
        this.mockMvc.perform(get("/affiliations")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden());
    }
  
    @Test
    @WithMockUser(roles={"ADMIN", "USER"})
    public void getAffiliationsAdmin() throws Exception {
        this.mockMvc.perform(get("/affiliations")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"));
    }

}