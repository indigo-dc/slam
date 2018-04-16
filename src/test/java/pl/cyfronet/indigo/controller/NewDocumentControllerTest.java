package pl.cyfronet.indigo.controller;

/**
 * Created by piotr on 13.07.16.
 */

//public class NewDocumentControllerTest extends MockMvcSecurityTest {
//
//    @InjectMocks
//    private NewDocumentController controller;
//
//    @Mock
//    private ActionContextFactory contextFactory;
//
//    @Mock
//    private EngineFacade engineFacade;

//    TODO FIX IT, INSTEAD OF COMMENTING
//    @Test
//    public void testGetDocument() throws Exception {
//        MockitoAnnotations.initMocks(this);
//
//        // Security Context
//        Team team = new Team();
//        team.setName("test");
//        Affiliation affiliation = new Affiliation();
//        affiliation.setStatus("ACTIVE");
//        User user = User.builder().id(500L).teams(Arrays.asList(team)).affiliations(Arrays.asList(affiliation)).build();
//        team.setMembers(Arrays.asList(user));
//        affiliation.setOwner(user);
//        List<GrantedAuthority> auth = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
//        PortalUser pu = PortalUser.builder().principal(UserInfo.fromUser(user)).user(user).authorities(auth).isAuthenticated(true).build();
//        SecurityContextHolder.getContext().setAuthentication(pu);
//        //
//
//        CreateGrantData grantData = new CreateGrantData();
//        grantData.setTeam("test");
//        grantData.setBranchOfScienceId("test");
//        grantData.setId("1");
//        grantData.setAffiliationId(1);
//
//        Document component = new Document();
//        component.setName(grantData.getId());
//        component.setTeam(grantData.getTeam());
//
//        Document document2 = new Document();
//        document2.setName(grantData.getId());
//        document2.setTeam(grantData.getTeam());
//        document2.setId("123");
//
//        ActionContext context1 = new ActionContext();
//        context1.addDocument("documentDraftFromController", component);
//
//        ActionContext context2 = new ActionContext();
//        context2.addDocument("documentDraftFromController", component);
//        context2.addDocument("newRoot", document2);
//
//        Mockito.when(contextFactory.createInstance(component)).thenReturn(context2);
//        Mockito.when(engineFacade.runAction(context1, "createNewRequest")).thenReturn(new ActionContext());
//
//        Response<ActionResponse> response = controller.getDocument(grantData);
//
//        Assert.assertEquals("REDIRECT",response.getData().getType().toString());
//    }

//}
