package pl.cyfronet.ltos.repository;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import pl.cyfronet.ltos.bean.Affiliation;
import pl.cyfronet.ltos.repository.validation.AffiliationValidator;

/**
 * Created by piotr on 14.07.16.
 */
public class ValidationTest {

    @Test
    public void testAffiliation() throws Exception {
        Affiliation affiliation = new Affiliation();
        affiliation.setName("test");
        affiliation.setDepartment("test");
        affiliation.setAddress("test");
        affiliation.setZipCode("test");
        affiliation.setCity("test");
        affiliation.setCountry("test");

        AffiliationValidator validator = new AffiliationValidator();
        Errors errors = new BeanPropertyBindingResult(affiliation, "affiliation");

        Assert.assertTrue(validator.supports(Affiliation.class));
        validator.validate(affiliation, errors);
        Assert.assertFalse(errors.hasErrors());
    }

}
