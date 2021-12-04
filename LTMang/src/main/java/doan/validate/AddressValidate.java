package doan.validate;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import doan.model.Address;

@Component
public class AddressValidate implements Validator {

	@Autowired
	private MessageSource messageSource;

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public void validate(Object object, Errors errors) {

	}

	public void validate(Address address, Errors errors) {
		if (address.getProvince().getProvinceId() == 0) {
			errors.rejectValue("province", null,
					messageSource.getMessage("selectProvinceError", null, Locale.getDefault()));
		}
		if (address.getDistrict().getDistrictId() == 0) {
			errors.rejectValue("district", null,
					messageSource.getMessage("selectDistrictError", null, Locale.getDefault()));
		}
		if (address.getWard().getWardId() == 0) {
			errors.rejectValue("ward", null, messageSource.getMessage("selectWardError", null, Locale.getDefault()));
		}
	}

}
