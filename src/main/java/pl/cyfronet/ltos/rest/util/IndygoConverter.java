package pl.cyfronet.ltos.rest.util;

import com.agreemount.bean.document.Document;
import com.agreemount.slaneg.db.DocumentOperations;
import com.agreemount.slaneg.db.RelationOperations;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.cyfronet.ltos.rest.bean.IndygoWrapper;
import pl.cyfronet.ltos.rest.bean.preferences.Preference;
import pl.cyfronet.ltos.rest.bean.preferences.Preferences;
import pl.cyfronet.ltos.rest.bean.preferences.Priority;
import pl.cyfronet.ltos.rest.bean.sla.Sla;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by km on 11.07.16.
 */
@Log4j
@Component
public class IndygoConverter {

    @Autowired
    private RelationOperations relationOperations;

    @Autowired
    private DocumentOperations documentOperations;

    public IndygoWrapper convertSlasListForRestApi(List<Document> slas, String login) {
        IndygoWrapper result = IndygoWrapper.builder().
                preferences(preparePreferences(slas, login)).
                sla(prepareSlaList(slas, login)).build();
        return result;
    }

    private Preferences preparePreferences(List<Document> slas, String login) {
        Preferences result = new Preferences();
        result.setCustomer(login);
        // TODO: id do zrobienia?
        result.setId("TOBEDONE");
        List<Priority> computePriorities = new ArrayList<>();
        List<Priority> storagePriorities = new ArrayList<>();
        for (Document sla : slas) {
            List<String> relations = relationOperations.getDocumentIdsWithRelationOnLeft(sla.getId(), "is_connected_SLA_to_Offer", "");
            if (sla.getState("serviceType").equals("computing")) {
                Priority priority = Priority.builder().
                        sla_id(sla.getId()).
                        service_id(relations.get(0)).
                        weight(Double.parseDouble(sla.getMetrics().get("weightComputing").toString())).build();
                computePriorities.add(priority);
            } else {
                Priority priority = Priority.builder().
                        sla_id(sla.getId()).
                        service_id(relations.get(0)).
                        weight(Double.parseDouble(sla.getMetrics().get("weightStorage").toString())).build();
                storagePriorities.add(priority);
            }
        }
        result.setPreferences(preparePreference(computePriorities, storagePriorities));
        return result;
    }

    private List<Preference> preparePreference(List<Priority> computePriorities, List<Priority> storagePriorities) {
        Preference computing = Preference.builder().service_type("compute").priority(computePriorities).build();
        Preference storage = Preference.builder().service_type("storage").priority(storagePriorities).build();
        List<Preference> preferences = Arrays.asList(computing, storage);
        return preferences;
    }

    private List<Sla> prepareSlaList(List<Document> slas, String login) {
        List<Sla> result = new ArrayList<>();
        for (Document doc : slas) {
            List<String> relations = relationOperations.getDocumentIdsWithRelationOnLeft(doc.getId(), "is_connected_SLA_to_Offer", "");
            Document provider = documentOperations.getDocument(relations.get(0));
            Sla sla = new Sla().builder().customer(login).id(doc.getId()).provider(provider.getName()).build();
            if (doc.getState("serviceType").equals("computing")) {
                sla.setStart_date(doc.getMetrics().get("startComp").toString());
                sla.setEnd_date(doc.getMetrics().get("endComp").toString());
            } else {
                sla.setStart_date(doc.getMetrics().get("startStorage").toString());
                sla.setEnd_date(doc.getMetrics().get("endStorage").toString());
            }
            result.add(sla);
        }
        return result;
    }
}
