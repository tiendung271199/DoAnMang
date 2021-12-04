package doan.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import doan.model.RegisterCharity;
import doan.service.RegisterCharityService;

@Component
public class RegisterCharityUtil {

	@Autowired
	private RegisterCharityService registerCharityService;

	public boolean checkRegister(int postId, int userId) {
		RegisterCharity registerCharity = registerCharityService.findByPostIdAndUserId(postId, userId);
		if (registerCharity != null) {
			if (registerCharity.getStatus() == 1) {
				return true;
			}
		}
		return false;
	}

	/*
	 * String key = "register" + id + userLogin.getId();
	 * session.setAttribute(key, "registered");
	 * 
	 * User userLogin = (User) session.getAttribute("userLogin");
	 * Post post = (Post) request.getAttribute("post");
	 * String key = "register" + post.getId() + userLogin.getId();
	 * if (session.getAttribute(key) == null) {}
	 */

}
