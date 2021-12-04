package doan.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import doan.constant.URLConstant;
import doan.model.User;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserAPIController {

	@GetMapping(URLConstant.URL_USER_LOGIN_API)
	public User getUserLogin(HttpSession session) {
		return (User) session.getAttribute("userLogin");
	}

}
