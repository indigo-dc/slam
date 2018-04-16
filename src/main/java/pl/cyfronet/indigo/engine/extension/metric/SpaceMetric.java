package pl.cyfronet.indigo.engine.extension.metric;

import com.agreemount.bean.metric.Metric;
import com.agreemount.bean.metric.MetricOption;
import pl.cyfronet.indigo.repository.OneDataRepository;
import pl.cyfronet.indigo.repository.onedata.Space;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SpaceMetric extends Metric<String> {
    public static OneDataRepository oneDataClient;

    public SpaceMetric() {
        super(InputType.SELECT);
    }

    public List<MetricOption> getOptions() {
        return oneDataClient.getUserSpaces().stream()
                .map(this::toMetricOption).collect(Collectors.toList());
    }

    private MetricOption toMetricOption(Space space) {
        MetricOption op = new MetricOption();
        op.setValue(space.spaceId);

        Map<String, Object> attrs = new HashMap<>();
        attrs.put("label", space.name);
        op.setAttributes(attrs);

        return op;
    }
}
