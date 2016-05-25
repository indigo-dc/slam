package pl.cyfronet.ltos.repository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import pl.cyfronet.ltos.bean.User;

public class UserRepositoryTest extends RestfulRepositoryTest<User> {

	private static final Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class); 

	@Autowired
    private UserRepository repo;
    
    @Test
    @WithMockUser(roles={})
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
	@WithMockUser(username="login1", roles={"USER"})
    public void getHimself() throws Exception {
		User user = User.builder().login("login1").name("marian").surname("testowy").build();
		repo.save(user);
		User user2 = User.builder().login("login2").name("lucjan").surname("testowy").build();
		repo.save(user2);
		// TODO refactor using example:
		// blog: http://blog.techdev.de/testing-a-secured-spring-data-rest-service-with-java-8-and-mockmvc/
		// code: https://github.com/techdev-solutions/spring-test-example
        this.mockMvc.perform(get("/users/" + user.getId())
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(resultEq(user));
	}
	
	@Test
    @WithMockUser(username="login2", roles={"USER"})
    public void getOtherUser() throws Exception {
		User user = User.builder().login("login1").name("marian").surname("testowy").build();
		repo.save(user);
		User user2 = User.builder().login("login2").name("lucjan").surname("testowy").build();
		repo.save(user2);
        this.mockMvc.perform(get("/users/1")
            	.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isForbidden());	
	}
	
}