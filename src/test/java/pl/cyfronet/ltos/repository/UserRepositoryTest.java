package pl.cyfronet.ltos.repository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

public class UserRepositoryTest extends RepositoryTest {

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
    
}