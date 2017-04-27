package pl.cyfronet.bazaar.engine.extension.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.cyfronet.bazaar.engine.extension.component.DocumentService;
import pl.cyfronet.ltos.repository.DocumentWeightRepository;

/**
 * Created by mszostak on 27.04.17.
 */
@Service
public class SpringContext {

    private static ApplicationContext context;
    private static DocumentWeightRepository documentWeightRepository;
    private static DocumentService documentService;

    public static DocumentWeightRepository getDocumentWeightRepository() {
        return documentWeightRepository;
    }

    @Autowired
    public void setDocumentWeightRepository(DocumentWeightRepository documentWeightRepository) {
        SpringContext.documentWeightRepository = documentWeightRepository;
    }
    public static ApplicationContext getApplicationContext() {
        return context;
    }


    @Autowired
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringContext.context = context;
    }


    public static DocumentService getDocumentService() {
        return documentService;
    }

    @Autowired
    public void setDocumentService(DocumentService documentService) {
        SpringContext.documentService = documentService;
    }

}
