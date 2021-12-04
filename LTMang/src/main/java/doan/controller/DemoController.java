package doan.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DemoController {

	@GetMapping("demo")
	public String demo(Model model, HttpSession session) {
		return "demo";
	}

	@PostMapping("demo")
	public String demo(@RequestParam String date, @RequestParam String datePresent, Model model) {
		return "demo";
	}

}
