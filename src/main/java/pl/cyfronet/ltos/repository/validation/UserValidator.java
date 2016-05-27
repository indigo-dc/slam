package pl.cyfronet.ltos.repository.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import pl.cyfronet.ltos.bean.User;

public class UserValidator implements Validator  {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		/*
		 * Third parameter refers to messages.properties entry
		 */
		ValidationUtils.rejectIfEmpty(errors, "name", "user.noname");
		ValidationUtils.rejectIfEmpty(errors, "surname", "user.nosurname");
		ValidationUtils.rejectIfEmpty(errors, "email", "user.noemail");
	}

}