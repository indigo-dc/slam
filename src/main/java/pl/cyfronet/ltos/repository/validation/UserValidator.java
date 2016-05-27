package pl.cyfronet.ltos.repository.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pl.cyfronet.ltos.bean.User;

public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		/*
		 * TODO handle exceptions properly on spring level and then uncomment line below 
		 */
		//ValidationUtils.rejectIfEmpty(errors, "email", "email.required");
		User user = (User) target;
		if (user.getEmail() == null || user.getEmail().isEmpty()) {
			errors.reject("");
		}

	}

}