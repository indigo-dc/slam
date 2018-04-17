package pl.cyfronet.indigo.repository.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import pl.cyfronet.indigo.bean.Affiliation;

/**
 * @author bwilk
 *
 */
public class AffiliationValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Affiliation.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        /*
         * Third parameter refers to messages.properties entry
         */
        ValidationUtils.rejectIfEmpty(errors, "name", "affiliation.noinstname");
        ValidationUtils.rejectIfEmpty(errors, "department", "affiliation.nodep");
        ValidationUtils.rejectIfEmpty(errors, "address", "affiliation.noaddr");
        ValidationUtils.rejectIfEmpty(errors, "zipCode", "affiliation.nozip");
        ValidationUtils.rejectIfEmpty(errors, "city", "affiliation.nocity");
        ValidationUtils.rejectIfEmpty(errors, "country", "affiliation.nocountry");
        
        
    }

}