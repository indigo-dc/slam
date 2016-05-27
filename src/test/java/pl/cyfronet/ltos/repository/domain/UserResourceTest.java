package pl.cyfronet.ltos.repository.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static pl.cyfronet.ltos.repository.DomainResourceTestMatchers.isAccessible;
import static pl.cyfronet.ltos.repository.DomainResourceTestMatchers.isCreated;
import static pl.cyfronet.ltos.repository.DomainResourceTestMatchers.isForbidden;
import static pl.cyfronet.ltos.repository.DomainResourceTestMatchers.isNoContent;

import java.util.function.Function;

import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;

import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.repository.AbstractResourceTest;

public class UserResourceTest extends AbstractResourceTest<User> {

	private final Function<User, MockHttpSession> owningUser;
	private final Function<User, MockHttpSession> otherUser;

	public UserResourceTest() {
		owningUser = user -> user(user.getId());
		otherUser = user -> user(user.getId() + 1);
	}

	@Test
	public void rootWithAdmin() throws Exception {
		assertThat(collectionWith(admin()), isAccessible());
	}

	@Test
	public void rootWithEmployee() throws Exception {
		assertThat(collectionWith(user()), isForbidden());
	}

	@Test
	public void oneWithOwning() throws Exception {
		assertThat(oneWith(owningUser), isAccessible());
	}

	@Test
	public void oneWithOther() throws Exception {
		assertThat(oneWith(otherUser), isForbidden());
	}

	@Test
	public void createWithAdmin() throws Exception {
		assertThat(createWith(admin()), isCreated());
	}

	@Test
	public void createWithUser() throws Exception {
		assertThat(createWith(user()), isForbidden());
	}

	@Test
	public void updateWithAdmin() throws Exception {
		assertThat(updateWith(admin()), isNoContent());
	}

	@Test
	public void updateWithOtherUser() throws Exception {
		assertThat(updateWith(otherUser), isForbidden());
	}

	@Test
	public void updateWithOwningUser() throws Exception {
		assertThat(updateWith(owningUser), isNoContent());
	}

	@Test
	public void deleteWithAdmin() throws Exception {
		assertThat(removeWith(admin()), isNoContent());
	}

	@Override
	protected String getResourceName() {
		return "users";
	}

}
