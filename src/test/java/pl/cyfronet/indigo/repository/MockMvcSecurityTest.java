package pl.cyfronet.indigo.repository;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import pl.cyfronet.indigo.ApplicationTest;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles("test")
@SpringApplicationConfiguration(classes = ApplicationTest.class)
public abstract class MockMvcSecurityTest {

    protected MockMvc mockMvc;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public final void initMockMvc() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).addFilter(filterChainProxy).build();
    }

    private MockHttpSession buildSession(Authentication authentication) {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, new MockSecurityContext(authentication));
        return session;
    }

    /**
     * @return a session for an employee with the id 500.
     */
    protected MockHttpSession user() {
        return user(500L);
    }

    protected MockHttpSession user(Long id) {
        return buildSession(AuthenticationMocks.userAuthentication(id));
    }

    /**
     * @return a session for an admin with the id 0.
     */
    protected MockHttpSession admin() {
        return admin(0L);
    }

    protected MockHttpSession admin(Long id) {
        return buildSession(AuthenticationMocks.adminAuthentication(id));
    }

    private static class MockSecurityContext implements SecurityContext {
        private Authentication authentication;

        public MockSecurityContext(Authentication authentication) {
            this.authentication = authentication;
        }

        @Override
        public Authentication getAuthentication() {
            return authentication;
        }

        @Override
        public void setAuthentication(Authentication authentication) {
            this.authentication = authentication;
        }
    }
}
