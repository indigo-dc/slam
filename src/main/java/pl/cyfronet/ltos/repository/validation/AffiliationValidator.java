package pl.cyfronet.ltos.repository.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import pl.cyfronet.ltos.bean.Affiliation;

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
        ValidationUtils.rejectIfEmpty(errors, "institution", "affiliation.noinst");
        ValidationUtils.rejectIfEmpty(errors, "department", "affiliation.nodep");
    }

}