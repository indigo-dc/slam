package pl.cyfronet.ltos.rest;

import com.agreemount.slaneg.db.DocumentOperations;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.cyfronet.ltos.repository.MockMvcSecurityTest;
import pl.cyfronet.ltos.repository.UserRepository;
import pl.cyfronet.ltos.rest.bean.IndigoWrapper;
import pl.cyfronet.ltos.rest.bean.sla.Sla;
import pl.cyfronet.ltos.rest.controller.RestController;
import pl.cyfronet.ltos.rest.logic.IndigoRestLogic;
import pl.cyfronet.ltos.rest.util.IndigoConverter;

import java.util.*;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by piotr on 18.07.16.
 */

public class RestControllerTest extends MockMvcSecurityTest {



    @InjectMocks
    private RestController restController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private IndigoRestLogic indigoRestLogic;

    @Mock
    private IndigoConverter converter;

    @Mock
    private DocumentOperations documentOperations;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void GetSLAs() throws Exception {

        Sla sla = mock(Sla.class);
        when(sla.getId()).thenReturn("1111L");
        when(indigoRestLogic.getSLAs(null, null, null, null, null)).thenReturn(Arrays.asList(sla));

        HashMap<String, String> urlVariables = new HashMap<String, String>();
        urlVariables.put("site", "BARI");
        urlVariables.put("id", "test_document");

        List<Sla> result = restController.getSLAs(null, null, null, null, null);
        Assert.assertEquals(Arrays.asList(sla), result);
    }

    @Test
    public void getSLAsTest() throws Exception {
        Sla sla = mock(Sla.class);
        String id = anyString();

        when(indigoRestLogic.getSLA(id)).thenReturn(sla);
        Sla sla2 = restController.getSLA(id);

        Assert.assertEquals(sla2, sla);

    }

    @Test
    public void getUserTest() throws Exception {
        IndigoWrapper userWrapper = mock(IndigoWrapper.class);

        String login = anyString();

        when(indigoRestLogic.getDataForLogin(login)).thenReturn(userWrapper);

        IndigoWrapper result = restController.getUser(login);

        Assert.assertEquals(userWrapper, result);


    }

    @Test
    public void getUserInfoTest() throws Exception {

        List<IndigoWrapper> listUsers = new ArrayList<IndigoWrapper>();
        String login = "aaa";

        List<IndigoWrapper> result = restController.getUsers(login);

        Assert.assertEquals(listUsers, result);


    }
}
