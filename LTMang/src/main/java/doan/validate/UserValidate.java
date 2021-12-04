package doan.validate;

import java.util.Locale;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import doan.constant.DatabaseConstant;
import doan.constant.GlobalConstant;
import doan.constant.RegexConstant;
import doan.model.User;
import doan.service.UserService;

@Component
public class UserValidate implements Validator {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public void validate(Object object, Errors errors) {

	}

	public void validateUsername(User user, Errors errors, User oldUser) {
		if (!user.getUsername().equals(GlobalConstant.EMPTY)) {
			if (!Pattern.matches(RegexConstant.REGEX_USERNAME, user.getUsername())) {
				errors.rejectValue("username", null,
						messageSource.getMessage("formatUsernameError", null, Locale.getDefault()));
			}
		}
		if (oldUser == null) {
			oldUser = new User(GlobalConstant.EMPTY, GlobalConstant.EMPTY, GlobalConstant.EMPTY);
		}
		if (userService.findUserDuplicate(user, oldUser, "username") != null) {
			errors.rejectValue("username", null,
					messageSource.getMessage("duplicateUsernameError", null, Locale.getDefault()));
		}
		if (userService.findUserDuplicate(user, oldUser, "email") != null) {
			errors.rejectValue("email", null,
					messageSource.getMessage("duplicateEmailError", null, Locale.getDefault()));
		}
		if (userService.findUserDuplicate(user, oldUser, "phone") != null) {
			errors.rejectValue("phone", null,
					messageSource.getMessage("duplicatePhoneError", null, Locale.getDefault()));
		}
	}

	public void validatePassword(User user, Errors errors, User oldUser, String confirmPassword, String oldPassword,
			Model model) {
		if (!user.getPassword().trim().equals(GlobalConstant.EMPTY)) {
			if (!Pattern.matches(RegexConstant.REGEX_PASSWORD, user.getPassword())) {
				errors.rejectValue("password", null,
						messageSource.getMessage("formatPasswordError", null, Locale.getDefault()));
			}
			if (oldUser != null) {
				if (bCryptPasswordEncoder.matches(user.getPassword(), oldUser.getPassword())) {
					errors.rejectValue("password", null,
							messageSource.getMessage("duplicatePasswordError", null, Locale.getDefault()));
				}
			}
			if (confirmPassword.equals(GlobalConstant.EMPTY)) {
				errors.rejectValue("password", null, GlobalConstant.EMPTY);
				model.addAttribute("confirmPasswordError",
						messageSource.getMessage("emptyConfirmPasswordError", null, Locale.getDefault()));
			} else {
				if (!user.getPassword().equals(confirmPassword)) {
					errors.rejectValue("password", null, GlobalConstant.EMPTY);
					model.addAttribute("confirmPasswordError",
							messageSource.getMessage("confirmPasswordError", null, Locale.getDefault()));
				}
			}
		} else {
			errors.rejectValue("password", null,
					messageSource.getMessage("emptyPasswordError", null, Locale.getDefault()));
		}
		if (oldPassword != null) {
			if (oldPassword.equals(GlobalConstant.EMPTY)) {
				errors.rejectValue("password", null, GlobalConstant.EMPTY);
				model.addAttribute("oldPasswordError",
						messageSource.getMessage("emptyPasswordError", null, Locale.getDefault()));
			} else {
				if (!bCryptPasswordEncoder.matches(oldPassword, oldUser.getPassword())) {
					errors.rejectValue("password", null, GlobalConstant.EMPTY);
					model.addAttribute("oldPasswordError",
							messageSource.getMessage("oldPasswordError", null, Locale.getDefault()));
				}
			}
		}
	}

	public void validatePhone(User user, Errors errors) {
		if (!user.getPhone().equals(GlobalConstant.EMPTY)) {
			if (!Pattern.matches(RegexConstant.REGEX_PHONE, user.getPhone())) {
				errors.rejectValue("phone", null,
						messageSource.getMessage("formatPhoneError", null, Locale.getDefault()));
			}
		}
	}

	public void validateRole(User user, Errors errors) {
		if (user.getRole().getId() == 0) {
			errors.rejectValue("role", null, messageSource.getMessage("selectRoleError", null, Locale.getDefault()));
		}
		if (user.getRole().getId() == DatabaseConstant.ROLE_ADMIN) {
			errors.rejectValue("role", null, messageSource.getMessage("notAddAdminError", null, Locale.getDefault()));
		}
	}

	public void validateRoleUserRegister(User user, Errors errors) {
		validateRole(user, errors);
		if (user.getRole().getId() == DatabaseConstant.ROLE_MOD) {
			errors.rejectValue("role", null, messageSource.getMessage("roleAddError", null, Locale.getDefault()));
		}
	}

}
