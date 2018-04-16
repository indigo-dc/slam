package pl.cyfronet.indigo.rest;

import com.agreemount.bean.document.Document;
import com.agreemount.slaneg.db.DocumentOperations;
import com.agreemount.slaneg.db.RelationOperations;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.cyfronet.indigo.rest.util.IndigoConverter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by piotr on 18.07.16.
 */

public class IndigoConverterTest {

    @InjectMocks
    private IndigoConverter indigoConverter;

    @Mock
    private RelationOperations relationOperations;

    @Mock
    private DocumentOperations documentOperations;

    @Test
    public void testConvertSlasListForRestApi() throws Exception {
        MockitoAnnotations.initMocks(this);

        Document document = new Document();
        document.setId("1");
        document.setState("serviceType", "computing");
        Map metrics = new HashMap<String, String>();
        metrics.put("weightComputing", "1");
        metrics.put("startComp", "2016-07-11");
        metrics.put("endComp", "2016-07-28");
        document.setMetrics(metrics);

        List<Document> slas = Arrays.asList(document);

        List<String> relations = Arrays.asList("4401ac5dc8cfbbb737b0a02575e6f4bc");
        Mockito.when( relationOperations.getDocumentIdsWithRelationOnLeft(Arrays.asList("1"), "is_connected_SLA_to_Offer", "") )
                .thenReturn(relations);

        Document provider = new Document();
        provider.setName("provider-UPV-GRyCAP");
        provider.setId("1234");
        Mockito.when( documentOperations.getDocument(relations.get(0)) ).thenReturn(provider);

//        IndigoWrapper indigoWrapper = indigoConverter.convertSlasListForRestApi(slas, "user");
//        Assert.assertNotNull(indigoWrapper);
//        Assert.assertEquals("user", indigoWrapper.getPreferences().getCustomer());
    }

}
