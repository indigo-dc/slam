

import com.esotericsoftware.yamlbeans.YamlReader;
import groovy.util.logging.Log4j;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by toszep on 2017-03-28.
 */
@Log4j
public class YamlParsingTest {

    @Test
    public void testParsingMetricsYaml() {
        readYaml("/rules/metrics.yml");
    }

    @Test
    public void testParsingMetricCategoriesYaml() {
        readYaml("/rules/metricCategories.yml");
    }

    @Test
    public void testParsingActionsYaml() {
        readYaml("/rules/actions.yml");
    }

    @Test
    public void testParsingQueriesYaml() {
        readYaml("/rules/queries.yml");
    }

    @Test
    public void testParsingQueryCategoriesYaml() {
        readYaml("/rules/queryCategories.yml");
    }

    @Test
    public void testParsingStatesYaml() {
        readYaml("/rules/states.yml");
    }

    @Test
    public void testMessageYamlProvider() {
        readYaml("/rules/messages.yml");
    }

    private void  readYaml(String resourceName ) {
        InputStream in = this.getClass().getResourceAsStream(resourceName);
        InputStreamReader br = new InputStreamReader(in);

        YamlReader reader;
        Object object = null;
        try {
            reader = new YamlReader(br);
            object = reader.read();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
