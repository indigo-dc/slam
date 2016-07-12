package pl.cyfronet.ltos.rest.logic;

import com.agreemount.bean.document.Document;
import org.springframework.stereotype.Component;
import pl.cyfronet.ltos.rest.bean.IndygoWrapper;
import pl.cyfronet.ltos.rest.util.IndygoConverter;

import java.util.Arrays;

/**
 * Created by km on 11.07.16.
 */
@Component
public class IndygoRestLogic {

    public IndygoWrapper getDataForLogin(String login) {
        //TODO: pobrac liste actywnych sla dla danego loginu
        IndygoWrapper result = IndygoConverter.convertSlasListForRestApi(Arrays.asList(new Document()), login);
        return result;
    }
}
