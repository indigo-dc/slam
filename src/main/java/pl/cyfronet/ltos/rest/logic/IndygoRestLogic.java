package pl.cyfronet.ltos.rest.logic;

import com.agreemount.bean.document.Document;
import com.agreemount.slaneg.db.DocumentOperations;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import pl.cyfronet.ltos.rest.bean.IndygoWrapper;
import pl.cyfronet.ltos.rest.util.IndygoConverter;

import java.util.List;

/**
 * Created by km on 11.07.16.
 */
@Component
@Log4j
public class IndygoRestLogic {

    @Autowired
    private DocumentOperations documentOperations;

    @Autowired
    private IndygoConverter converter;

    public IndygoWrapper getDataForLogin(String login) {
        IndygoWrapper result = converter.convertSlasListForRestApi(getDocuments(login), login);
        return result;
    }

    private List<Document> getDocuments(String login) {
        Query query =new Query();
        query.addCriteria(Criteria.where("author").is(login));
        List<Document> result = documentOperations.getDocumentsWithQuery(query);
        return result;
    }
}
