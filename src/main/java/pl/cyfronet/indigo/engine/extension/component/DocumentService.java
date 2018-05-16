package pl.cyfronet.indigo.engine.extension.component;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.cyfronet.indigo.repository.DocumentWeightRepository;


@Service(value = "documentservice")
public class DocumentService {
    @Autowired
    DocumentWeightRepository documentWeightRepository;

    @Value("${cmdb.url}")
    @Getter
    private String cmdbUrl;

    @Getter
    @Autowired
    SitesService sitesService;

    @Getter
    @Autowired
    ProviderService providerService;

    public DocumentService() {

    }
}
