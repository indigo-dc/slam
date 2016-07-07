package pl.cyfronet.bazaar.engine.extension.constraint.action.impl;

import com.agreemount.bean.document.Document;
import com.agreemount.bean.identity.provider.IdentityProvider;
import com.agreemount.slaneg.action.ActionContext;
import com.agreemount.slaneg.constraint.action.impl.QualifierImpl;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import pl.cyfronet.bazaar.engine.extension.constraint.action.definition.HasAnyOfMetrics;

import java.util.List;

/**
 * Created by chomik on 27.01.16.
 */
@Component
@Scope("prototype")
public class HasAnyOfMetricsImpl extends QualifierImpl<HasAnyOfMetrics,ActionContext> {

    @Autowired
    private IdentityProvider identityProvider;

    @Override
    public boolean isAvailable() {
        String alias = getConstraintDefinition().getDocumentAlias();
        Preconditions.checkArgument(!StringUtils.isEmpty(alias), "Document alias is not set");
        Document document = getActionContext().getDocument(alias);
        Preconditions.checkNotNull(document, "Document stored under alias [%s] was not found", alias);

        List<String> metrics = getConstraintDefinition().getMetrics();

        Preconditions.checkArgument(!CollectionUtils.isEmpty(metrics), "No metrics set");

        Boolean result = false;

        for (String metricId : metrics) {
            if (document.getMetrics().containsKey(metricId)) {
                result = true;
                break;
            }
        }

        return result;
    }
}