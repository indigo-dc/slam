package pl.cyfronet.bazaar;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.Test;
import org.mockito.Mockito;

import pl.cyfronet.bazaar.engine.extension.constraint.action.definition.HasAnyOfMetrics;
import pl.cyfronet.bazaar.engine.extension.constraint.action.impl.HasAnyOfMetricsImpl;

import com.agreemount.bean.document.Document;
import com.agreemount.slaneg.action.ActionContext;
import com.agreemount.slaneg.constraint.action.definition.ActionConstraint;
import com.agreemount.slaneg.constraint.action.impl.QualifierImpl;


public class HasAnyOfMetricsImplTest {

    @Test
    public void testDefinition() throws Exception {
  
        HasAnyOfMetricsImpl spy = Mockito.spy(new HasAnyOfMetricsImpl());
        Mockito.mock(QualifierImpl.class);
        HasAnyOfMetrics metrics = new HasAnyOfMetrics();        
        Document document = new Document();
        metrics.setMetrics(Arrays.asList("asdasda"));
        //document.setMetrics(metrics2);
        ActionContext<Document> actionContext = new ActionContext<Document>(document);
        // Prevent/stub logic in super.method()
        Mockito.doReturn(metrics).when((QualifierImpl)spy).getConstraintDefinition();
        Mockito.doReturn(actionContext).when((QualifierImpl)spy).getActionContext();
        // When
        spy.isAvailable();
     
        // Then
       // Mockito.verify(spy).load();
        
    }

}
