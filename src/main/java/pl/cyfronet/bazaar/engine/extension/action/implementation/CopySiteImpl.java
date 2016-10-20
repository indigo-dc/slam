package pl.cyfronet.bazaar.engine.extension.action.implementation;

import com.agreemount.bean.document.Document;
import com.agreemount.slaneg.action.ActionContext;
import com.agreemount.slaneg.action.definition.AddMetric;
import com.agreemount.slaneg.action.impl.AbstractActionImpl;
import com.google.common.base.Preconditions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import pl.cyfronet.bazaar.engine.extension.action.definition.CopySite;
import pl.cyfronet.bazaar.engine.extension.bean.IndigoDocument;

/**
 * Created by Paweł Szepieniec pawel.szepieniec@gmail.com on 01.03.15.
 */
@Component
@Scope("prototype")
public class CopySiteImpl extends AbstractActionImpl<CopySite, IndigoDocument, ActionContext<IndigoDocument>> {

    public void run(ActionContext actionContext) {
        IndigoDocument from = (IndigoDocument)actionContext.getDocument(definition.getFromAlias());
        Preconditions.checkNotNull(from, "document was not found, alias [%s]", definition.getFromAlias());

        Document to = actionContext.getDocument(definition.getToAlias());
        Preconditions.checkNotNull(to, "document was not found, alias [%s]", definition.getToAlias());

        //FIXME need support casting/copy
        IndigoDocument indigoDocumentTo = new IndigoDocument();
        indigoDocumentTo.setMetrics(to.getMetrics());
        indigoDocumentTo.setAuthor(to.getAuthor());
        indigoDocumentTo.setStates(to.getStates());
        indigoDocumentTo.setId(to.getId());
        indigoDocumentTo.setName(to.getName());
        indigoDocumentTo.setRootUuid(to.getRootUuid());
        indigoDocumentTo.setSlaUuid(to.getSlaUuid());
        indigoDocumentTo.setIsLeaf(to.getIsLeaf());
        indigoDocumentTo.setSite(from.getSite());
        indigoDocumentTo.setSiteName(from.getSiteName());

        documentOperations.saveDocument(indigoDocumentTo);

        actionContext.addDocument(definition.getToAlias(), indigoDocumentTo);

    }
}
