package pl.cyfronet.indigo.repository.domain;

import static org.hamcrest.MatcherAssert.assertThat;

//public class UserResourceTest extends AbstractResourceTest<User> {

//	private final Function<User, MockHttpSession> owningUser;
//	private final Function<User, MockHttpSession> otherUser;
//
//	public UserResourceTest() {
//		owningUser = user -> user(user.getId());
//		otherUser = user -> user(user.getId() + 1);
//	}
//
//	@Test
//	public void rootWithAdmin() throws Exception {
//		assertThat(collectionWith(admin()), isAccessible());
//	}
//
//
//	@Ignore
//	@Test
//	public void rootWithEmployee() throws Exception {
//		assertThat(collectionWith(user()), isForbidden());
//	}
//
//	@Test
//	public void oneWithOwning() throws Exception {
//		assertThat(oneWith(owningUser), isAccessible());
//	}
//
//
//	@Ignore
//	@Test
//	public void oneWithOther() throws Exception {
//		assertThat(oneWith(otherUser), isForbidden());
//	}
//
//	@Test
//	public void createWithAdmin() throws Exception {
//		assertThat(createWith(admin()), isCreated());
//	}
//
//
//	@Ignore
//	@Test
//	public void createWithUser() throws Exception {
//		assertThat(createWith(user()), isForbidden());
//	}
//
//	@Test
//	public void updateWithAdmin() throws Exception {
//		assertThat(updateWith(admin()), isNoContent());
//	}
//
//	@Ignore
//	@Test
//	public void updateWithOtherUser() throws Exception {
//		assertThat(updateWith(otherUser), isForbidden());
//	}
//
//	@Test
//	public void updateWithOwningUser() throws Exception {
//		assertThat(updateWith(owningUser), isNoContent());
//	}
//
//	@Test
//	public void deleteWithAdmin() throws Exception {
//		assertThat(removeWith(admin()), isNoContent());
//	}
//
//	@Override
//	protected String getResourceName() {
//		return "users";
//	}

//}
