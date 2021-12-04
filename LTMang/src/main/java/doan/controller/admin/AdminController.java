package doan.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import doan.constant.URLConstant;
import doan.constant.ViewNameConstant;

@Controller
public class AdminController {

	@GetMapping(URLConstant.URL_ADMIN)
	public String index() {
		return ViewNameConstant.ADMIN_INDEX;
	}

	@GetMapping(URLConstant.URL_ADMIN_LOGIN)
	public String login() {
		return ViewNameConstant.ADMIN_LOGIN;
	}

}
