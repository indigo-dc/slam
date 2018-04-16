package pl.cyfronet.indigo.controller;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class NewDocumentController {

    static Logger logger = LoggerFactory
            .getLogger(NewDocumentController.class);

    @Value("${cmdb.url}")
    private String cmdbUrl;

    @RequestMapping(value = "api/sites", method = RequestMethod.GET)
    @ResponseBody
    public String getSites() throws UnirestException {
        return Unirest.get(cmdbUrl+"/cmdb/service/list").asString().getBody();
    }
}
