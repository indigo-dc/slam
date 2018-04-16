package pl.cyfronet.indigo.engine.extension.constraint.action.impl;

import com.agreemount.bean.document.Document;
import com.agreemount.slaneg.action.ActionContext;
import com.agreemount.slaneg.constraint.action.impl.QualifierImpl;
import com.google.common.base.Preconditions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import pl.cyfronet.indigo.engine.extension.constraint.action.definition.IsPublicService;
import pl.cyfronet.indigo.repository.CmdbRepository;

@Component
@Scope("prototype")
public class IsPublicServiceImpl extends QualifierImpl<IsPublicService, ActionContext> {

    public static CmdbRepository cmdbRepository;

    @Override
    public boolean isAvailable() {
        String alias = getConstraintDefinition().getDocumentAlias();
        Preconditions.checkArgument(!StringUtils.isEmpty(alias), "Document alias is not set");
        Document document = getActionContext().getDocument(alias);
        Preconditions.checkNotNull(document, "Document stored under alias [%s] was not found", alias);
        String metricId = getConstraintDefinition().getMetricId();
        Preconditions.checkArgument(!StringUtils.isEmpty(metricId), "MetricId is not set");
        String id = (String) document.getMetrics().get(metricId);
        Preconditions.checkArgument(!StringUtils.isEmpty(metricId), "Metric [%s] is not set on this document", metricId);

        boolean result = false;
        try {
            result = (boolean) cmdbRepository.getById("service", id).getJSONObject("data").get("is_public_service");
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        Preconditions.checkNotNull(result, "Service [%s] doesn't have is_public_service parameter", id);
        return result ? true : false;
    }
}