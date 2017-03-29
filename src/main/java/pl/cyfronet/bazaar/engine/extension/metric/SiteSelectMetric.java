package pl.cyfronet.bazaar.engine.extension.metric;

import com.agreemount.bean.metric.Metric;
import com.agreemount.bean.metric.MetricOption;
import com.agreemount.bean.metric.SelectMetric;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Transient;

import java.util.*;

@lombok.Getter
@lombok.Setter
@lombok.ToString
@lombok.EqualsAndHashCode(callSuper = false)
public class SiteSelectMetric extends Metric<String> {
    public SiteSelectMetric() {
        super(InputType.SELECT);
    }

//    @Value("${cmdb.url}")
    private String cmdbUrl = "http://indigo.cloud.plgrid.pl";

    public List<MetricOption> getOptions() {
        JSONArray sites = null;
        try {
            sites = Unirest.get(cmdbUrl + "/cmdb/service/list").asJson().getBody().getObject().getJSONArray("rows");
        } catch (UnirestException e) {
            //@TODO@ - provide message to frontend
            e.printStackTrace();
        }

        ArrayList<MetricOption> ret = new ArrayList<MetricOption>();

        for(int i=0; i < sites.length(); ++i) {
            MetricOption opt = new MetricOption();
            JSONObject site = sites.getJSONObject(i);

            Map<String, Object> attrs = new HashMap<>();

            String hostname = site.getJSONObject("value").has("hostname") ? site.getJSONObject("value").getString("hostname") : null;
            String sitename = site.getJSONObject("value").has("sitename") ? site.getJSONObject("value").getString("sitename") : null;

            attrs.put("hostname",  hostname);
            attrs.put("sitename",  sitename);
            opt.setAttributes(attrs);
            opt.setValue(site.getString("id"));
            ret.add(opt);
        }

        return ret;
    }
}