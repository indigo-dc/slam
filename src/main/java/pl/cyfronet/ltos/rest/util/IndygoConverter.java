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
import pl.cyfronet.ltos.rest.bean.sla.Service;
import pl.cyfronet.ltos.rest.bean.sla.Sla;
import pl.cyfronet.ltos.rest.bean.sla.Target;

import java.util.*;
import java.util.stream.Collectors;

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
                addPriorities(computePriorities, sla, relations.get(0),"weightComputing");
            } else {
                addPriorities(storagePriorities, sla, relations.get(0),"weightStorage");
            }
        }
        result.setPreferences(preparePreference(computePriorities, storagePriorities));
        return result;
    }

    private void addPriorities(List<Priority> priorities, Document sla, String serviceId ,String weight) {
        Priority priority = Priority.builder().
                sla_id(sla.getId()).
                service_id(serviceId).
                weight(Double.parseDouble(sla.getMetrics().get(weight).toString())).build();
        priorities.add(priority);
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
            sla.setServices(prepareServices(doc, provider));
            result.add(sla);
        }
        return result;
    }

    private List<Service> prepareServices(Document sla, Document offer) {
        List<Service> result = Arrays.asList(Service.builder().type(sla.getState("serviceType")).
                service_id(offer.getId()).targets(prepareTargets(sla)).build());

        return result;
    }

    private List<Target> prepareTargets(Document sla) {
        List<Target> result = new ArrayList<>();
        addRestrictions(sla, result, "computingTime", prepareTarget("computingTime", "h", sla));
        addRestrictions(sla, result, "publicIP", prepareTarget("publicIP", "none", sla));
        addRestrictions(sla, result, "numCpus", prepareTarget("numCpus", "none", sla));
        addRestrictions(sla, result, "memSize", prepareTarget("memSize", "MB", sla));
        addRestrictions(sla, result, "diskSize", prepareTarget("diskSize", "MB", sla));
        addRestrictions(sla, result, "uploadBandwith", prepareTarget("uploadBandwith", "Mbps", sla));
        addRestrictions(sla, result, "downloadBandwith", prepareTarget("downloadBandwith", "Mbps", sla));
        addRestrictions(sla, result, "uploadAggregated", prepareTarget("uploadAggregated", "MB", sla));
        addRestrictions(sla, result, "downloadAggregated", prepareTarget("downloadAggregated", "MB", sla));
        addRestrictions(sla, result, "costs", prepareTarget("costs", "EUR", sla));
        addRestrictions(sla, result, "storage", prepareTarget("storage", "GB", sla));
        return result;
    }

    private void addRestrictions(Document sla, List<Target> result, String type, Target gb) {
        if (!sla.getMetrics().entrySet().stream().
                filter(a -> a.getKey().startsWith(type)).
                collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue())).entrySet().isEmpty()) {
            result.add(gb);
        }
    }

    private Target prepareTarget(String type, String unit, Document sla) {
        Target result = Target.builder().type(type).unit(unit).build();
        Map <String,Object> restrictions = new HashMap<>();
        for (Map.Entry<String, Object> entry : sla.getMetrics().entrySet().stream().
                filter(a -> a.getKey().startsWith(type)).collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue())).entrySet()) {
            restrictions.put(entry.getKey().substring(type.length()+1),entry.getValue());
        }
        if(restrictions.isEmpty()) {
            return null;
        }
        result.setRestrictions(restrictions);
        return result;
    }
}
