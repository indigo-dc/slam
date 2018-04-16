package pl.cyfronet.indigo.rest.util;

import com.agreemount.bean.document.Document;
import com.agreemount.bean.metric.Metric;
import com.agreemount.engine.facade.MetricFacade;
import lombok.extern.log4j.Log4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.cyfronet.indigo.engine.extension.bean.IndigoDocument;
import pl.cyfronet.indigo.rest.bean.IndigoWrapper;
import pl.cyfronet.indigo.rest.bean.preferences.Preference;
import pl.cyfronet.indigo.rest.bean.preferences.Preferences;
import pl.cyfronet.indigo.rest.bean.preferences.Priority;
import pl.cyfronet.indigo.rest.bean.sla.Service;
import pl.cyfronet.indigo.rest.bean.sla.Sla;
import pl.cyfronet.indigo.rest.bean.sla.Target;

import java.util.*;

/**
 * Created by km on 11.07.16.
 */
@Log4j
@Component
public class IndigoConverter {

    @Autowired
    private MetricFacade metricFacade;

    public IndigoWrapper convertSlasListForRestApi(List<Document> slas, String login) {
        IndigoWrapper result = IndigoWrapper.builder().
                preferences(Arrays.asList(preparePreferences(slas, login))).
                sla(prepareSlaList(slas, login)).build();
        return result;
    }

    private Preferences preparePreferences(List<Document> slas, String login) {
        Preferences result = new Preferences();
        result.setCustomer(login);
        result.setId(DigestUtils.sha1Hex(login));
        List<Priority> computePriorities = new ArrayList<>();
        List<Priority> storagePriorities = new ArrayList<>();
        for (Document sla : slas) {
            if (sla.getState("serviceType").equals("computing")) {
                addPriorities(computePriorities, sla, ((IndigoDocument)sla).getSite(), "weightComputing");
            } else {
                addPriorities(storagePriorities, sla, ((IndigoDocument)sla).getSite(), "weightStorage");
            }
        }
        result.setPreferences(preparePreference(computePriorities, storagePriorities));
        return result;
    }

    private void addPriorities(List<Priority> priorities, Document sla, String serviceId, String weight) {
        Priority priority = Priority.builder().
                sla_id(sla.getId()).
                service_id(serviceId).
                //TODO - RESTORE IT
//                weight(Double.parseDouble(sla.getMetrics().get(weight).toString())).build();
                weight(0.5).build();
        priorities.add(priority);
    }

    private List<Preference> preparePreference(List<Priority> computePriorities, List<Priority> storagePriorities) {
        Preference computing = Preference.builder().service_type("compute").priority(computePriorities).build();
        Preference storage = Preference.builder().service_type("storage").priority(storagePriorities).build();
        List<Preference> preferences = Arrays.asList(computing, storage);
        return preferences;
    }

    public List<Sla> prepareSlaList(List<Document> slas, String login) {
        List<Sla> result = new ArrayList<>();
        for (Document _doc : slas) {
            IndigoDocument doc = (IndigoDocument)_doc;
            Document provider = null;
            Sla.SlaBuilder slaBuilder = Sla.builder().id(doc.getId()).provider(doc.getSiteName());

            if (login != null)
                slaBuilder.customer(login);

            Sla sla = slaBuilder.build();

            if (doc.getState("serviceType") != null) {
                if (doc.getState("serviceType").equals("computing")) {
                    //TODO can this be so uncomplete?
                    if (doc.getMetrics().containsKey("endComp") && doc.getMetrics().get("endComp") != null)
                        sla.setEnd_date(doc.getMetrics().get("endComp").toString());
                    if (doc.getMetrics().containsKey("startComp") && doc.getMetrics().get("startComp") != null)
                        sla.setStart_date(doc.getMetrics().get("startComp").toString());
                } else {
                    if (doc.getMetrics().containsKey("startStorage") && doc.getMetrics().get("startStorage") != null)
                        sla.setStart_date(doc.getMetrics().get("startStorage").toString());
                    if (doc.getMetrics().containsKey("endStorage") && doc.getMetrics().get("endStorage") != null)
                        sla.setEnd_date(doc.getMetrics().get("endStorage").toString());
                }
            }
            sla.setServices(prepareServices(doc));
            result.add(sla);
        }
        return result;
    }

    private List<Service> prepareServices(IndigoDocument sla) {
        return Arrays.asList(Service.builder().type(sla.getState("serviceType")).
                service_id(sla.getSite()).targets(prepareTargets(sla)).build());
    }

    private List<Target> prepareTargets(Document sla) {
        List<Metric> documentMetrics = metricFacade.fetchMetrics();

        Map<String, Target> restrictions = new HashMap<>();

        for(Metric metric : documentMetrics) {

            if(sla.getMetrics().containsKey(metric.getId()) && metric.getId().contains("-")) {
                String key = metric.getId().split("-")[0];
                String constraint = metric.getId().split("-")[1];
                if(!restrictions.containsKey(key)) {
                    restrictions.put(key, Target.builder().type(key).unit(metric.getUnit()).restrictions(new HashMap<>()).build());
                }

                restrictions.get(key).getRestrictions().put(constraint, sla.getMetrics().get(metric.getId()));
            }
        }

        return new ArrayList<>(restrictions.values());
    }
}
