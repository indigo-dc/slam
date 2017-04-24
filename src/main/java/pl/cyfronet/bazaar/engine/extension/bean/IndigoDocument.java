package pl.cyfronet.bazaar.engine.extension.bean;

import com.agreemount.bean.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.cyfronet.bazaar.engine.extension.component.DocumentService;

import java.util.Arrays;
import java.util.List;

/**
 * Created by km on 03.08.16.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IndigoDocument extends Document{
    /** Extend this variable when adding new sla types */
    static final List<String> siteMetricIds = Arrays.asList("siteStorageSelect",
                                                            "siteComputeSelect");

    private DocumentService documentService;

    public IndigoDocument() {
        this.documentService = DocumentService.getInstance();
    }

    public Integer getWeight() {
        return 0;
    }

    public String getSite(){
        String s = documentService.getSitesService().getSites();
        for (String metricId : siteMetricIds){
            if(getMetrics().containsKey(metricId))
                return getMetrics().get(metricId).toString();
        }
        return site;
    }

    public String getSiteName(){
        if(siteName == null)
            siteName = getSite();
        return siteName;
    }
    private String site = null;
    private String siteName = null;
}
