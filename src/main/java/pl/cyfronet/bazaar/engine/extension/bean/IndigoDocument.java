package pl.cyfronet.bazaar.engine.extension.bean;

import com.agreemount.bean.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import pl.cyfronet.ltos.rest.util.SitesService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by km on 03.08.16.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IndigoDocument extends Document{
    /** Extend this variable when adding new sla types */
    static final List<String> siteMetricIds = Arrays.asList("siteStorageSelect",
                                                            "siteComputeSelect");

//    @Autowired
//    private SitesService sitesService;

    public String getSite(){
//        String s = sitesService.getSites();

        for (String metricId : siteMetricIds){
            if(getMetrics().containsKey(metricId))
                return getMetrics().get(metricId).toString();
        }
        return null;
    }

    public String getSiteName(){
        return getSite();
    }
//    private String siteName;
}
