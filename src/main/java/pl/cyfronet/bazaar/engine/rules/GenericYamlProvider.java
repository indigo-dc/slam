package pl.cyfronet.bazaar.engine.rules;

import com.agreemount.slaneg.fixtures.RulesProvider;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.google.common.base.Preconditions;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;


@Log4j
public class GenericYamlProvider<E> implements RulesProvider<E> {

    @Value("${engine.cacheYamls:true}")
    private boolean cacheYamls;
    private static final String PATH = "rules/";
    private final String module;
    private List<E> items = null;

    @Autowired
    private ResourceLoader resourceLoader;

    public GenericYamlProvider(String module) {
        this.module = module;
    }

    @Override
    public List<E> getItems() {

        if (cacheYamls && items != null) {
            return items;
        }

        Resource resource = resourceLoader.getResource("classpath:" + PATH + module + ".yml");

        YamlReader reader;
        Object object = null;
        try {
            reader = new YamlReader(new InputStreamReader(resource.getInputStream()));
            object = reader.read();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }

        Preconditions.checkNotNull(object, "opened yaml source for module %s is null", module);
        Map map = (Map) object;

        items = (List<E>) map.get(module);
        return items;
    }

    public boolean isCached(){
        return cacheYamls;
    }
}