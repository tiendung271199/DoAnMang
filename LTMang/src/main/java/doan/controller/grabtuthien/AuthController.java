package doan.controller.grabtuthien;

import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import doan.constant.DatabaseConstant;
import doan.constant.URLConstant;
import doan.constant.ViewNameConstant;
import doan.model.Address;
import doan.model.User;
import doan.service.AddressService;
import doan.service.DistrictService;
import doan.service.PostService;
import doan.service.ProvinceService;
import doan.service.RoleService;
import doan.service.UserService;
import doan.service.WardService;
import doan.util.StringUtil;
import doan.validate.AddressValidate;
import doan.validate.UserValidate;

@Controller
public class AuthController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidate userValidate;

	@Autowired
	private ProvinceService provinceService;

	@Autowired
	private DistrictService districtService;

	@Autowired
	private WardService wardService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private AddressValidate addressValidate;

	@Autowired
	private RoleService roleService;

	@ModelAttribute
	public void getListPost(Model model) {
		model.addAttribute("listNew", postService.getListNew());
		model.addAttribute("listHighlight", postService.getListHighlight());
	}

	@GetMapping(URLConstant.URL_LOGIN)
	public String login() {
		return ViewNameConstant.LOGIN;
	}

	@PostMapping(URLConstant.URL_LOGIN)
	public String login(@RequestParam String username, @RequestParam String password, HttpSession session,
			RedirectAttributes ra, Model model) {
		model.addAttribute("username", username);
		User user = userService.findByUsername(username);
		if (user == null) {
			model.addAttribute("error", messageSource.getMessage("loginError", null, Locale.getDefault()));
			return ViewNameConstant.LOGIN;
		}
		if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
			model.addAttribute("error", messageSource.getMessage("loginError", null, Locale.getDefault()));
			return ViewNameConstant.LOGIN;
		}
		if (user.getRole().getId() == DatabaseConstant.ROLE_ADMIN
				|| user.getRole().getId() == DatabaseConstant.ROLE_MOD) {
			ra.addFlashAttribute("error", messageSource.getMessage("loginAuthError", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_LOGIN;
		}
		session.setAttribute("userLogin", user);
		ra.addFlashAttribute("success", messageSource.getMessage("loginSuccess", null, Locale.getDefault()));
		return "redirect:/" + URLConstant.URL_INDEX;
	}

	@GetMapping(URLConstant.URL_LOGOUT)
	public String logout(HttpSession session, RedirectAttributes ra) {
		session.removeAttribute("userLogin");
		ra.addFlashAttribute("success", messageSource.getMessage("logoutSuccess", null, Locale.getDefault()));
		return "redirect:/" + URLConstant.URL_LOGIN;
	}

	@GetMapping(URLConstant.URL_PROFILE)
	public String profile(Model model, RedirectAttributes ra, HttpSession session) {
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noUserLogin", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_LOGIN;
		}
		Address address = userLogin.getAddress();
		model.addAttribute("user", userLogin);
		model.addAttribute("address", address);
		model.addAttribute("listProvince", provinceService.getAll());
		model.addAttribute("listDistrict", districtService.findByProvinceId(address.getProvince().getProvinceId()));
		model.addAttribute("listWard", wardService.findByDistrictId(address.getDistrict().getDistrictId()));
		return ViewNameConstant.PROFILE;
	}

	@Transactional
	@PostMapping(URLConstant.URL_PROFILE)
	public String profile(@Valid @ModelAttribute("userError") User user, BindingResult userRs,
			@Valid @ModelAttribute("addressError") Address address, BindingResult addressRs, RedirectAttributes ra,
			Model model, HttpSession session) {
		User userLogin = (User) session.getAttribute("userLogin");
		model.addAttribute("listProvince", provinceService.getAll());
		if (address.getProvince().getProvinceId() > 0) {
			model.addAttribute("listDistrict", districtService.findByProvinceId(address.getProvince().getProvinceId()));
			if (address.getDistrict().getDistrictId() > 0) {
				model.addAttribute("listWard", wardService.findByDistrictId(address.getDistrict().getDistrictId()));
			}
		}
		model.addAttribute("user", user);
		model.addAttribute("address", address);
		userValidate.validateUsername(user, userRs, userLogin);
		userValidate.validatePhone(user, userRs);
		addressValidate.validate(address, addressRs);
		if (userRs.hasErrors() || addressRs.hasErrors()) {
			model.addAttribute("error", messageSource.getMessage("error2", null, Locale.getDefault()));
			return ViewNameConstant.PROFILE;
		}
		address.setId(userLogin.getAddress().getId());
		if (addressService.update(address) > 0) {
			System.out.println("Cập nhật địa chỉ thành công");
		} else {
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_PROFILE;
		}
		user.setRole(userLogin.getRole());
		user.setId(userLogin.getId());
		if (userService.update(user) > 0) {
			session.setAttribute("userLogin", userService.findById(userLogin.getId()));
			ra.addFlashAttribute("success", messageSource.getMessage("updateSuccess", null, Locale.getDefault()));
		} else {
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
		}
		return "redirect:/" + URLConstant.URL_PROFILE;
	}

	@GetMapping(URLConstant.URL_PROFILE_CHANGE_PASSWORD)
	public String changePassword(Model model, RedirectAttributes ra, HttpSession session) {
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noUserLogin", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_LOGIN;
		}
		return ViewNameConstant.PROFILE_CHANGE_PASSWORD;
	}

	@PostMapping(URLConstant.URL_PROFILE_CHANGE_PASSWORD)
	public String changePassword(@Valid @ModelAttribute("userError") User user, BindingResult rs,
			@RequestParam String oldPassword, @RequestParam String confirmPassword, Model model, RedirectAttributes ra,
			HttpSession session) {
		User userLogin = (User) session.getAttribute("userLogin");
		model.addAttribute("user", user);
		userValidate.validatePassword(user, rs, userLogin, confirmPassword, oldPassword, model);
		if (rs.hasFieldErrors("password")) {
			return ViewNameConstant.PROFILE_CHANGE_PASSWORD;
		}
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setId(userLogin.getId());
		if (userService.updatePassword(user) > 0) {
			ra.addFlashAttribute("success",
					messageSource.getMessage("changePasswordSuccess", null, Locale.getDefault()));
		} else {
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
		}
		return "redirect:/" + URLConstant.URL_PROFILE;
	}

	@GetMapping(URLConstant.URL_REGISTER_ACCOUNT)
	public String registerAccount(Model model) {
		model.addAttribute("listRole", roleService.getUserRole());
		model.addAttribute("listProvince", provinceService.getAll());
		return ViewNameConstant.REGISTER_ACCOUNT;
	}

	@Transactional
	@PostMapping(URLConstant.URL_REGISTER_ACCOUNT)
	public String registerAccount(@Valid @ModelAttribute("userError") User user, BindingResult userRs,
			@Valid @ModelAttribute("addressError") Address address, BindingResult addressRs,
			@RequestParam String confirmPassword, Model model, RedirectAttributes ra) {
		user = StringUtil.dataProcessingUser(user);
		model.addAttribute("listRole", roleService.getUserRole());
		model.addAttribute("listProvince", provinceService.getAll());
		if (address.getProvince().getProvinceId() > 0) {
			model.addAttribute("listDistrict", districtService.findByProvinceId(address.getProvince().getProvinceId()));
			if (address.getDistrict().getDistrictId() > 0) {
				model.addAttribute("listWard", wardService.findByDistrictId(address.getDistrict().getDistrictId()));
			}
		}
		model.addAttribute("user", user);
		model.addAttribute("address", address);
		userValidate.validateUsername(user, userRs, null);
		userValidate.validatePassword(user, userRs, null, confirmPassword, null, model);
		userValidate.validatePhone(user, userRs);
		userValidate.validateRoleUserRegister(user, userRs);
		addressValidate.validate(address, addressRs);
		if (userRs.hasErrors() || addressRs.hasErrors()) {
			return ViewNameConstant.REGISTER_ACCOUNT;
		}
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		if (addressService.save(address) > 0) {
			user.setAddress(addressService.getNewestAddress());
		} else {
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_REGISTER_ACCOUNT;
		}
		if (userService.save(user) > 0) {
			ra.addFlashAttribute("success", messageSource.getMessage("addSuccess", null, Locale.getDefault()));
		} else {
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
		}
		return "redirect:/" + URLConstant.URL_LOGIN;
	}

}
