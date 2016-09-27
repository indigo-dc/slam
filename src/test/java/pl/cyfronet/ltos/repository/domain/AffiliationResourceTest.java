package pl.cyfronet.ltos.repository.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static pl.cyfronet.ltos.repository.DomainResourceTestMatchers.isAccessible;
import static pl.cyfronet.ltos.repository.DomainResourceTestMatchers.isForbidden;
import static pl.cyfronet.ltos.repository.DomainResourceTestMatchers.lalala;

import java.util.LinkedList;
import java.util.function.Function;

import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;

import pl.cyfronet.ltos.bean.Affiliation;
import pl.cyfronet.ltos.repository.AbstractResourceTest;

//public class AffiliationResourceTest extends AbstractResourceTest<Affiliation> {

//	private final Function<Affiliation, MockHttpSession> owningUser;
//	private final Function<Affiliation, MockHttpSession> otherUser;
//
//	public AffiliationResourceTest() {
//		owningUser = affiliation -> user(affiliation.getOwner().getId());
//		otherUser = affiliation -> user(affiliation.getOwner().getId() + 1);
//	}
//
//	@Test
//	public void rootWithAdmin() throws Exception {
//		assertThat(collectionWith(admin()), isAccessible());
//	}
//
//	@Test
//	public void rootWithUser() throws Exception {
//		assertThat(collectionWith(user()), isAccessible());
//	}
//
//	@Test
//	public void rootWithUserCheckFilter() throws Exception {
//		// TODO: fix this - check if they are filtered right
//		assertThat(collectionWith(user()), lalala(new LinkedList<Affiliation>()));
//	}
//
//	@Test
//	public void oneWithOwning() throws Exception {
//		assertThat(oneWith(owningUser), isAccessible());
//	}
//
//	@Test
//	public void oneWithOtherUser() throws Exception {
//		assertThat(oneWith(otherUser), isForbidden());
//	}
//
//	@Override
//	protected String getResourceName() {
//		return "affiliations";
//	}

//}
