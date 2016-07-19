package pl.cyfronet.bazaar;

import pl.cyfronet.bazaar.engine.extension.constraint.action.definition.HasAnyOfMetrics;

import org.junit.Test;
import org.junit.Assert;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by piotr on 12.07.16.
 */
public class HasAnyOfMetricsTest {

    @Test
    public void testDefinition() throws Exception {
        HasAnyOfMetrics definition = new HasAnyOfMetrics();

        List<String> metrics = new ArrayList<String>();
        metrics.add("Test1");
        metrics.add("Test2");

        definition.setMetrics(metrics);
        Assert.assertEquals("Setter", 2, definition.getMetrics().size());

        Assert.assertEquals("Getter", "BASE", definition.getDocumentAlias()); // default alias

    }

}
