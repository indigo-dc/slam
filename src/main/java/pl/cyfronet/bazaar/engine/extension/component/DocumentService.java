package pl.cyfronet.bazaar.engine.extension.component;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.cyfronet.ltos.controller.bazaar.BazaarConfig;
import pl.cyfronet.ltos.repository.DocumentWeightRepository;


//@Service
public class DocumentService {
    private static DocumentService instance = null;
    public static DocumentService getInstance() {
        if(instance == null) {
            ApplicationContext context = new AnnotationConfigApplicationContext(BazaarConfig.class);
            instance = (DocumentService) context.getBean("documentservice");
        }

        return instance;
    }

    @Autowired
    DocumentWeightRepository documentWeightRepository;

    @Value("${cmdb.url}")
    @Getter
    private String cmdbUrl;

    @Getter
    @Autowired
    SitesService sitesService;

    public DocumentService() {

    }
}
