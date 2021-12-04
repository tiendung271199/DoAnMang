package doan.validate;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import doan.constant.GlobalConstant;
import doan.model.Post;
import doan.util.FileUtil;

@Component
public class PostValidate implements Validator {

	@Autowired
	private MessageSource messageSource;

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public void validate(Object target, Errors errors) {

	}

	public void validateQuantity(Post post, Errors errors) {
		if (post.getQuantity() == 0) {
			errors.rejectValue("quantity", null,
					messageSource.getMessage("noQuantitySelected", null, Locale.getDefault()));
		}
	}

	public void validatePicture(MultipartFile multipartFile, Errors errors) {
		String fileName = multipartFile.getOriginalFilename();
		if (!fileName.equals(GlobalConstant.EMPTY)) {
			if (!FileUtil.checkFileExtension(fileName)) {
				errors.rejectValue("picture", null,
						messageSource.getMessage("formatPictureError", null, Locale.getDefault()));
			}
		} else {
			errors.rejectValue("picture", null,
					messageSource.getMessage("noPictureSelected", null, Locale.getDefault()));
		}
	}

	public void validatePictureUpdate(MultipartFile multipartFile, Errors errors) {
		String fileName = multipartFile.getOriginalFilename();
		if (!fileName.equals(GlobalConstant.EMPTY)) {
			if (!FileUtil.checkFileExtension(fileName)) {
				errors.rejectValue("picture", null,
						messageSource.getMessage("formatPictureError", null, Locale.getDefault()));
			}
		}
	}

}
