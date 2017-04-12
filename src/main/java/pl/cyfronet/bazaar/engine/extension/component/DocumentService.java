package pl.cyfronet.bazaar.engine.extension.component;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.cyfronet.ltos.repository.DocumentWeightRepository;


@Service
public class DocumentService {

    @Autowired
    DocumentWeightRepository documentWeightRepository;

    @Value("${cmdb.url}")
    @Getter
    private String cmdbUrl;

    @Getter
    @Autowired
    SitesService sitesService;

    public DocumentService() {
        int a = 0;
    }
}
