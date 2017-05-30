package pl.cyfronet.bazaar.engine.extension.bean;

import com.agreemount.bean.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.cyfronet.ltos.bean.DocumentWeight;
import pl.cyfronet.ltos.bean.DocumentWeightPk;
import pl.cyfronet.ltos.security.PortalUser;

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
    private String site = null;
    private String siteName = null;
}
