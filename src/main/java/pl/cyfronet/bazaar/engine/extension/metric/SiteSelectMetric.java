package pl.cyfronet.bazaar.engine.extension.metric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.PropertySource;

import com.agreemount.bean.metric.Metric;
import com.agreemount.bean.metric.MetricOption;

import pl.cyfronet.ltos.repository.CmdbRepository;

@lombok.Getter
@lombok.Setter
@lombok.ToString
@lombok.EqualsAndHashCode(callSuper = false)
@PropertySource("classpath:application.properties")
public class SiteSelectMetric extends Metric<String> {
    public SiteSelectMetric() {
        super(InputType.SELECT);
    }

    public enum SiteType {compute, storage}

    private SiteType siteType;
    public String cmdbUrl;

    public static CmdbRepository cmdbRepository;

    public List<MetricOption> getOptions() {
        JSONArray sites = null;
        ArrayList<MetricOption> ret = new ArrayList<>();
        try {
            sites = cmdbRepository.get("service", "type", siteType.toString()).getJSONArray("rows");
        } catch (Exception e) {
            //@TODO@ - provide message to frontend
            e.printStackTrace();
            return ret;
        }

        for(int i=0; i < sites.length(); ++i) {
            MetricOption opt = new MetricOption();
            JSONObject site = sites.getJSONObject(i);

            Map<String, Object> attrs = new HashMap<>();

            String hostname = site.getJSONObject("value").has("hostname") ? site.getJSONObject("value").getString("hostname") : null;
            String sitename = site.getJSONObject("value").has("sitename") ? site.getJSONObject("value").getString("sitename") : null;

            attrs.put("hostname",  hostname);
            attrs.put("sitename",  sitename);
            attrs.put("label",  (sitename != null ? sitename : "?") + " : " + (hostname != null ? hostname : "?"));
            opt.setAttributes(attrs);
            opt.setValue(site.getString("id"));
            ret.add(opt);
        }

        return ret;
    }
}