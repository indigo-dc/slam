package pl.cyfronet.bazaar.engine.extension.component;

import com.agreemount.slaneg.action.DocumentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.cyfronet.bazaar.engine.extension.bean.IndigoDocument;

/**
 * Created by mszostak on 12.04.17.
 */

@Service(value = "DocumentFactory")
public class IndigoDocumentFactory extends DocumentFactory<IndigoDocument> {
    public IndigoDocumentFactory() {
        super(IndigoDocument.class);
    }

    @Autowired
    private DocumentService documentService;

    @Override
    public IndigoDocument createInstance() {
        return new IndigoDocument(documentService);
    }
}
