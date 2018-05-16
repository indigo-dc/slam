package pl.cyfronet.indigo.engine.extension.bean;

import com.agreemount.bean.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.cyfronet.indigo.bean.DocumentWeight;
import pl.cyfronet.indigo.bean.DocumentWeightPk;
import pl.cyfronet.indigo.security.PortalUser;

import java.util.Arrays;
import java.util.List;

/**
 * Created by km on 03.08.16.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IndigoDocument extends Document{
    /** Extend this variable when adding new sla types */
    static final List<String> SITE_METRIC_IDS = Arrays.asList("siteStorageSelect",
                                                              "siteComputeSelect");

    public IndigoDocument() {
    }

    public Integer getWeight() {
        DocumentWeight documentWeight = SpringContext.getDocumentWeightRepository().findOne(new DocumentWeightPk(
                ((PortalUser) SecurityContextHolder.getContext().getAuthentication()).getUserBean().getId(), getId()));
        if (documentWeight == null)
            return 0;
        return documentWeight.getWeight();
    }

    public String getSite(){
        for (String metricId : SITE_METRIC_IDS){
            if(getMetrics().containsKey(metricId)) {
                site = getMetrics().get(metricId).toString();
                break;
            }
        }
        return site;
    }

    public String getSiteName(){
        if(siteName == null)
            siteName = SpringContext.getDocumentService().getSitesService().getSiteName(getSite());
        return siteName;
    }

    public String getProviderId(){
        if(providerId == null)
            providerId = SpringContext.getDocumentService().getProviderService().getProviderId(getSite());
        return providerId;
    }

    private String site = null;
    private String siteName = null;
    private String providerId = null;
}
