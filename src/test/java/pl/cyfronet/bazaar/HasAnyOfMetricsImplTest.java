package pl.cyfronet.bazaar;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.Test;
import org.mockito.Mockito;

import pl.cyfronet.bazaar.engine.extension.constraint.action.definition.HasAnyOfMetrics;
import pl.cyfronet.bazaar.engine.extension.constraint.action.impl.HasAnyOfMetricsImpl;

import com.agreemount.bean.document.Document;
import com.agreemount.slaneg.action.ActionContext;
import com.agreemount.slaneg.constraint.action.impl.QualifierImpl;


public class HasAnyOfMetricsImplTest {

  @Test
  public void testWithoutMetrics() throws Exception {

      HasAnyOfMetricsImpl spy = Mockito.spy(new HasAnyOfMetricsImpl());
      Mockito.mock(QualifierImpl.class);
      HasAnyOfMetrics metrics = new HasAnyOfMetrics();        
      Document document = new Document();
      metrics.setMetrics(Arrays.asList("sampleMetric"));
      ActionContext<Document> actionContext = new ActionContext<Document>(document);
      Mockito.doReturn(metrics).when((QualifierImpl)spy).getConstraintDefinition();
      Mockito.doReturn(actionContext).when((QualifierImpl)spy).getActionContext();
      spy.isAvailable();
  }
  
  @Test
  public void testWithMetrics() throws Exception {

      HasAnyOfMetricsImpl spy = Mockito.spy(new HasAnyOfMetricsImpl());
      Mockito.mock(QualifierImpl.class);
      HasAnyOfMetrics metrics = new HasAnyOfMetrics();        
      Document document = new Document();
      metrics.setMetrics(Arrays.asList("sampleMetric"));
      //document.setMetrics(metrics2);
      HashMap<String, Object> documentMetrics = new HashMap<String, Object>();
      documentMetrics.put("sampleMetric", "sampleMetricValue");
      document.setMetrics(documentMetrics);
      ActionContext<Document> actionContext = new ActionContext<Document>(document);
      // Prevent/stub logic in super.method()
      Mockito.doReturn(metrics).when((QualifierImpl)spy).getConstraintDefinition();
      Mockito.doReturn(actionContext).when((QualifierImpl)spy).getActionContext();
      // When
      spy.isAvailable();
  }

}
