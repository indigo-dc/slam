package pl.cyfronet.ltos.rest;

import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import org.springframework.transaction.annotation.Transactional;
import pl.cyfronet.ltos.bean.*;
import pl.cyfronet.ltos.repository.DocumentWeightRepository;
import pl.cyfronet.ltos.repository.MockMvcSecurityTest;
import pl.cyfronet.ltos.repository.UserRepository;
import pl.cyfronet.ltos.rest.controller.EngineExtensionController;
import pl.cyfronet.ltos.security.PortalUser;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by marta on 7/19/17.
 */


public class EngineExtensionControllerTest  extends MockMvcSecurityTest {


    @InjectMocks
    private EngineExtensionController controller;

    @Mock
    private DocumentWeightRepository documentWeightRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SessionFactory hibernateSessionFactory;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void getDocumentWeightsTest() throws Exception {

        User user = mock(User.class);
        PortalUser portalUser = mock(PortalUser.class);

        List<DocumentWeight> documentsWeights = getDocumentWeightList();

        when(portalUser.getUserBean()).thenReturn(user);
        when(user.getDocuments()).thenReturn(documentsWeights);

        List<DocumentWeight> result = controller.getDocumentWeights(portalUser);

        Assert.assertEquals(documentsWeights, result);
    }


    @Test
    @Transactional
    public void setDocumentWeightsTest() throws Exception {


        PortalUser portalUserMock = mock(PortalUser.class);
        Long id = 11111L;
        User user = mock(User.class);

        List<DocumentWeight> documentWeights = getDocumentWeightList();
        when(user.getDocuments()).thenReturn(documentWeights);
        when(portalUserMock.getUserBean()).thenReturn(user);
        when(user.getId()).thenReturn(id);

        user.setDocuments(documentWeights);

        when(userRepository.findOne(user.getId())).thenReturn(user);
        controller.setDocumentWeights(portalUserMock, documentWeights);

    }


    private List<DocumentWeight> getDocumentWeightList(){

        DocumentWeight document = mock(DocumentWeight.class);
        List<DocumentWeight> docs = new ArrayList<>();
        docs.add(document);
        return docs;
    }




}
