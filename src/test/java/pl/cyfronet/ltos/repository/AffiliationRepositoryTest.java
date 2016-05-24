package pl.cyfronet.ltos.repository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

public class AffiliationRepositoryTest extends RepositoryTest {
    
    @Test
    @WithMockUser(roles={})
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