package pl.cyfronet.ltos.repository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.ResultMatcher;

import pl.cyfronet.ltos.bean.User;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserRepositoryTest extends RepositoryTest {

	private static Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class); 

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
            .andExpect(content().contentType("application/json")).andDo(new ResultHandler() {
				
				@Override
				public void handle(MvcResult result) throws Exception {
					MockHttpServletResponse response = result.getResponse();
					ObjectMapper objectMapper = new ObjectMapper();
					Resource<User> customer = objectMapper.readValue(response.getContentAsString(), new TypeReference<Resource<User>>() {});
				
					logger.info("User: " + customer.getContent());
				}
			});
    }
	
	@Test
    @WithMockUser(username="login1", roles={"USER"})
    public void getYourself() throws Exception {
		User user = User.builder().login("login1").name("marian").surname("testowy").build();
		repo.save(user);
        this.mockMvc.perform(get("/users/1")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json")).andExpect(new ResultMatcher() {
				@Override
				public void match(MvcResult result) throws Exception {
					User returnedUser = parseResponse(result);
					returnedUser.setId(user.getId()); //  not serialized by hateos
					Assert.assertEquals(user, returnedUser);
				}
			});
	}
	
	@Test
    @WithMockUser(username="login1", roles={"ADMIN"})
    public void getOtherUser() throws Exception {
		User user = User.builder().login("login2").name("lucjan").surname("testowy").build();
		repo.save(user);
        this.mockMvc.perform(get("/users/1")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json")).andExpect(new ResultMatcher() {
				@Override
				public void match(MvcResult result) throws Exception {
					User returnedUser = parseResponse(result);
					returnedUser.setId(user.getId()); //  not serialized by hateos
					Assert.assertEquals(user, returnedUser);
				}
			});
	}
	
	private User parseResponse(MvcResult result) throws IOException,
			JsonParseException, JsonMappingException,
			UnsupportedEncodingException {
		MockHttpServletResponse response = result.getResponse();
		ObjectMapper objectMapper = new ObjectMapper();
		Resource<User> customer = objectMapper.readValue(response.getContentAsString(),	new TypeReference<Resource<User>>() {});
		return customer.getContent();
	}
    
}