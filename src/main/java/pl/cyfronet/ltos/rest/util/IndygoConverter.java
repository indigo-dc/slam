package pl.cyfronet.ltos.rest.util;

import com.agreemount.bean.document.Document;
import pl.cyfronet.ltos.rest.bean.IndygoWrapper;
import pl.cyfronet.ltos.rest.bean.preferences.Preferences;
import pl.cyfronet.ltos.rest.bean.sla.Sla;

import java.util.List;

/**
 * Created by km on 11.07.16.
 */
public class IndygoConverter {

    private IndygoConverter() {
    }

    public static IndygoWrapper convertSlasListForRestApi(List<Document> slas, String login) {
        IndygoWrapper result = IndygoWrapper.builder().
                preferences(preparePreferences(slas, login)).
                sla(prepareSlaList(slas, login)).build();
        return result;
    }

    private static Preferences preparePreferences(List<Document> slas, String login) {
        Preferences result = new Preferences();
        result.setCustomer(login);
        // TODO: foreach po liście i budowanie
        for(Document sla:slas){

        }
        return null;
    }

    private static List<Sla> prepareSlaList(List<Document> slas, String login) {
        // TODO:
        return null;
    }
}
