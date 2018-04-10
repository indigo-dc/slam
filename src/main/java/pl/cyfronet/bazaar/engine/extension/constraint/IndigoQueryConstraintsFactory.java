package pl.cyfronet.bazaar.engine.extension.constraint;

import com.agreemount.bean.identity.provider.IdentityProvider;
import com.agreemount.slaneg.action.ActionContext;
import com.agreemount.slaneg.constraint.query.CustomQueryConstraintsFactory;
import com.agreemount.slaneg.constraint.query.definition.QueryConstraint;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import pl.cyfronet.bazaar.engine.extension.constraint.query.definition.IsAdministratorInRelatedSite;

@Component
public class IndigoQueryConstraintsFactory implements CustomQueryConstraintsFactory {
    @Autowired
    private IdentityProvider identityProvider;

    @Override
    public Criteria buildCriteria(QueryConstraint queryConstraint, ActionContext actionContext) {
        Criteria criteria = null;

        if (queryConstraint instanceof IsAdministratorInRelatedSite) {
            IsAdministratorInRelatedSite def = (IsAdministratorInRelatedSite) queryConstraint;
            String siteMetricId = def.getSiteMetricId();
            Preconditions.checkArgument(!StringUtils.isEmpty(siteMetricId), "IsAdministratorInRelatedSite - siteMetricId is not set!");
            Preconditions.checkNotNull(identityProvider.getIdentity(), "identity is null");

            criteria = Criteria.where(siteMetricId).in(identityProvider.getIdentity().getAdministratedSites());
        }
        return criteria;
    }
}
