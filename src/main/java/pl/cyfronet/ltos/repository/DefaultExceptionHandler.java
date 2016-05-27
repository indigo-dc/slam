package pl.cyfronet.ltos.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.rest.webmvc.RepositoryRestExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice(basePackageClasses=DefaultExceptionHandler.class)
public class DefaultExceptionHandler extends RepositoryRestExceptionHandler {

    @Autowired
    public DefaultExceptionHandler(MessageSource messageSource) {
        super(messageSource);
    }
   
    
}