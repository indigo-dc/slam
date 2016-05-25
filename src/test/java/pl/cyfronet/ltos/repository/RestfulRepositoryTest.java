package pl.cyfronet.ltos.repository;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.hateoas.Resource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pl.cyfronet.ltos.ApplicationTest;
import pl.cyfronet.ltos.bean.User;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = ApplicationTest.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class RestfulRepositoryTest<T> {
	
	private static Logger logger = LoggerFactory.getLogger(RestfulRepositoryTest.class); 
	
    @Autowired
    private WebApplicationContext wac;

    protected MockMvc mockMvc;
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
        		.webAppContextSetup(wac)
        		.apply(springSecurity())
        		.build();
    }
    
    protected ResultMatcher resultEq(T object) {
    	return new ResultMatcher() {
			@Override
			public void match(MvcResult result) throws Exception {
				T responseObject = deserialize(result.getResponse().getContentAsString());
				Long id = getId(responseObject);
				setId(responseObject, getId(object)); // temporarly sets id - ignored by equals  
				// HATEOAS resource does not like ids 
				// there is no beter way to implement this for now
				// https://github.com/spring-projects/spring-hateoas/issues/67
				try {
					Assert.assertEquals(object, responseObject);
				} finally {
					setId(responseObject, id); // set id back
				}
			}
		};
    }

    protected ResultMatcher resultNotEq(T object) {
    	return new ResultMatcher() {
			@Override
			public void match(MvcResult result) throws Exception {
				T responseObject = deserialize(result.getResponse().getContentAsString());
				Long id = getId(responseObject);
				setId(responseObject, getId(object)); // temporarly sets id - ignored by equals  
				// HATEOAS resource does not like ids 
				// there is no beter way to implement this for now
				// https://github.com/spring-projects/spring-hateoas/issues/67
				try {
					Assert.assertNotEquals(object, responseObject);
				} finally {
					setId(responseObject, id); // set id back
				}
			}
		};
    }

    
	protected final T deserialize(String string) throws IOException {
		logger.debug("response: " + string);
		ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<Resource<User>> valueTypeRef = new TypeReference<Resource<User>>(){};
		Resource<T> object = objectMapper.readValue(string, valueTypeRef);
		logger.debug("typeref: " + object);
		return object.getContent();
	}
	
	protected String serialize(T object) throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(stream, object);
		return stream.toString();
	}
	
	protected Long getId(T obj) {
		try {
			Long id;
			Method getIdMethod = obj.getClass().getMethod("getId");
			id = (Long) getIdMethod.invoke(obj);
			return id;
		} catch (NoSuchMethodException e) {
			throw new IllegalStateException("Entity has no getId method");
		} catch (InvocationTargetException | IllegalAccessException e) {
			throw new IllegalStateException("Could not execute getId method");
		}
	}

	
	protected void setId(T obj, Long id) {
		try {
			Method getIdMethod = obj.getClass().getMethod("setId");
		getIdMethod.invoke(obj, id);
		} catch (NoSuchMethodException e) {
			throw new IllegalStateException("Entity has no getId method");
		} catch (InvocationTargetException | IllegalAccessException e) {
			throw new IllegalStateException("Could not execute getId method");
		}
	}

}