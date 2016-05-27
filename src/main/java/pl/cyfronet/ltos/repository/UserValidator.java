package pl.cyfronet.ltos.repository;

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
		 * TODO handle exceptions properly on spring level and then uncomment line below 
		 * Probably to get nice response we need some integration with RepositoryRestExceptionHandler
		 * but it's darn hard to get this line throw exception mapped by appropriate handler method
		 * for now rejectIfEmpty will end up with 500 because of some error-handler mapping rule
		 * check ValidatingRepositoryEventListener
		 */
		ValidationUtils.rejectIfEmpty(errors, "email", "user.noemail");
//		User user = (User) target;
//		if (user.getEmail() == null || user.getEmail().isEmpty()) {
//			errors.reject("");
//		}

	}

}