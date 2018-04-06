package pl.cyfronet.bazaar.engine.extension.action.impl;

import com.agreemount.bean.document.Document;
import com.agreemount.bean.identity.provider.IdentityProvider;
import com.agreemount.slaneg.action.ActionContext;
import com.agreemount.slaneg.action.impl.AbstractActionImpl;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.cyfronet.bazaar.engine.extension.action.definition.SetOrganizationAsTeam;

@Component
@Scope("prototype")
public class SetOrganizationAsTeamImpl extends AbstractActionImpl<SetOrganizationAsTeam, Document, ActionContext<Document>> {

    @Autowired
    private IdentityProvider identityProvider;

    @Override
    protected void run(ActionContext<Document> documentActionContext) {
        Document document = documentActionContext.getDocument(definition.getDocumentAlias());
        Preconditions.checkNotNull(document, "document was not found, alias [%s]", definition.getDocumentAlias());

        Preconditions.checkNotNull(identityProvider.getIdentity(), "No user logged");
        String organization = identityProvider.getIdentity().getTeamMembers().get(0).getTeam();
        document.getMetrics().put("teamId",organization);

        documentOperations.saveDocument(document);

        documentActionContext.addDocument(definition.getDocumentAlias(), document);
    }
}