package doan.controller.admin;

import java.util.List;
import java.util.Locale;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import doan.constant.DatabaseConstant;
import doan.constant.GlobalConstant;
import doan.constant.URLConstant;
import doan.constant.ViewNameConstant;
import doan.model.Address;
import doan.model.User;
import doan.service.AddressService;
import doan.service.DistrictService;
import doan.service.ProvinceService;
import doan.service.RoleService;
import doan.service.UserService;
import doan.service.WardService;
import doan.util.PageUtil;
import doan.util.StringUtil;
import doan.validate.AddressValidate;
import doan.validate.UserValidate;

@Controller
@RequestMapping(URLConstant.URL_ADMIN)
public class AdminUserController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

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
	private UserValidate userValidate;

	@GetMapping({ URLConstant.URL_ADMIN_USER, URLConstant.URL_ADMIN_USER_PAGINATION })
	public String index(@PathVariable(required = false) Integer page, Model model, RedirectAttributes ra) {
		int currentPage = GlobalConstant.DEFAULT_PAGE;
		if (page != null) {
			if (page < GlobalConstant.DEFAULT_PAGE) {
				ra.addFlashAttribute("error", messageSource.getMessage("pageError", null, Locale.getDefault()));
				return "redirect:/" + URLConstant.URL_ADMIN_USER_REDIRECT;
			}
			currentPage = page;
		}
		int offset = PageUtil.getOffset(currentPage);
		int totalRow = userService.totalRow();
		int totalPage = PageUtil.getTotalPage(totalRow);
		List<User> listUser = userService.getList(offset, GlobalConstant.TOTAL_ROW);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("totalRow", totalRow);
		model.addAttribute("listUser", listUser);
		return ViewNameConstant.ADMIN_USER_INDEX;
	}

	// tìm kiếm
	@GetMapping({ URLConstant.URL_ADMIN_USER_SEARCH, URLConstant.URL_ADMIN_USER_SEARCH_2,
			URLConstant.URL_ADMIN_USER_SEARCH_PAGINATION_2 })
	public String search(@PathVariable(required = false) Integer page, Model model, RedirectAttributes ra,
			@RequestParam(required = false) String username, @PathVariable(required = false) String usernameURL,
			@RequestParam(required = false) Integer roleId, @PathVariable(required = false) Integer roleURL) {
		if (usernameURL != null) {
			username = usernameURL;
		}
		if (roleURL != null) {
			roleId = roleURL;
		}
		if (username == null) {
			username = GlobalConstant.EMPTY;
		}
		if (roleId == null) {
			roleId = 0;
		}
		if (username.equals(GlobalConstant.EMPTY) && roleId == 0) {
			ra.addFlashAttribute("error", messageSource.getMessage("searchError", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_ADMIN_USER_REDIRECT;
		}
		int currentPage = GlobalConstant.DEFAULT_PAGE;
		if (page != null) {
			if (page < GlobalConstant.DEFAULT_PAGE) {
				ra.addFlashAttribute("error", messageSource.getMessage("pageError", null, Locale.getDefault()));
				return "redirect:/" + URLConstant.URL_ADMIN_USER_REDIRECT;
			}
			currentPage = page;
		}
		int offset = PageUtil.getOffset(currentPage);
		int totalRow = userService.totalRowSearch(username, roleId);
		int totalPage = PageUtil.getTotalPage(totalRow);
		List<User> listUser = userService.search(username, roleId, offset, GlobalConstant.TOTAL_ROW);
		model.addAttribute("username", username);
		model.addAttribute("roleId", roleId);
		model.addAttribute("search", "/tim-kiem");
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("totalRow", totalRow);
		model.addAttribute("listUser", listUser);
		return ViewNameConstant.ADMIN_USER_INDEX;
	}

	@GetMapping(URLConstant.URL_ADMIN_USER_ADD)
	public String add(Model model) {
		model.addAttribute("listRole", roleService.getNonAdminRole());
		model.addAttribute("listProvince", provinceService.getAll());
		return ViewNameConstant.ADMIN_USER_ADD;
	}

	@Transactional
	@PostMapping(URLConstant.URL_ADMIN_USER_ADD)
	public String add(@Valid @ModelAttribute("userError") User user, BindingResult userRs,
			@Valid @ModelAttribute("addressError") Address address, BindingResult addressRs,
			@RequestParam String confirmPassword, Model model, RedirectAttributes ra) {
		user = StringUtil.dataProcessingUser(user);
		model.addAttribute("listRole", roleService.getNonAdminRole());
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
		userValidate.validateRole(user, userRs);
		addressValidate.validate(address, addressRs);
		if (userRs.hasErrors() || addressRs.hasErrors()) {
			return ViewNameConstant.ADMIN_USER_ADD;
		}
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		if (addressService.save(address) > 0) {
			user.setAddress(addressService.getNewestAddress());
		} else {
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_ADMIN_USER_REDIRECT;
		}
		if (userService.save(user) > 0) {
			ra.addFlashAttribute("success", messageSource.getMessage("addSuccess", null, Locale.getDefault()));
		} else {
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
		}
		return "redirect:/" + URLConstant.URL_ADMIN_USER_REDIRECT;
	}

	@GetMapping(URLConstant.URL_ADMIN_USER_UPDATE)
	public String update(@PathVariable int id, Model model, RedirectAttributes ra) {
		User user = userService.findById(id);
		if (user == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noData", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_ADMIN_USER_REDIRECT;
		}
		if (user.getRole().getId() == DatabaseConstant.ROLE_ADMIN) {
			ra.addFlashAttribute("error", messageSource.getMessage("notUpdateAdminError", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_ADMIN_USER_REDIRECT;
		}
		Address address = user.getAddress();
		model.addAttribute("user", user);
		model.addAttribute("address", address);
		model.addAttribute("listRole", roleService.getNonAdminRole());
		model.addAttribute("listProvince", provinceService.getAll());
		model.addAttribute("listDistrict", districtService.findByProvinceId(address.getProvince().getProvinceId()));
		model.addAttribute("listWard", wardService.findByDistrictId(address.getDistrict().getDistrictId()));
		return ViewNameConstant.ADMIN_USER_UPDATE;
	}

	@Transactional
	@PostMapping(URLConstant.URL_ADMIN_USER_UPDATE)
	public String update(@Valid @ModelAttribute("userError") User user, BindingResult userRs,
			@Valid @ModelAttribute("addressError") Address address, BindingResult addressRs, RedirectAttributes ra,
			Model model) {
		User oldUser = userService.findById(user.getId());
		model.addAttribute("listRole", roleService.getNonAdminRole());
		model.addAttribute("listProvince", provinceService.getAll());
		if (address.getProvince().getProvinceId() > 0) {
			model.addAttribute("listDistrict", districtService.findByProvinceId(address.getProvince().getProvinceId()));
			if (address.getDistrict().getDistrictId() > 0) {
				model.addAttribute("listWard", wardService.findByDistrictId(address.getDistrict().getDistrictId()));
			}
		}
		model.addAttribute("user", user);
		model.addAttribute("address", address);
		userValidate.validateUsername(user, userRs, oldUser);
		userValidate.validatePhone(user, userRs);
		userValidate.validateRole(user, userRs);
		addressValidate.validate(address, addressRs);
		if (userRs.hasErrors() || addressRs.hasErrors()) {
			return ViewNameConstant.ADMIN_USER_UPDATE;
		}
		if (addressService.update(address) > 0) {
			System.out.println("Cập nhật địa chỉ thành công");
		} else {
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_ADMIN_USER_REDIRECT;
		}
		if (userService.update(user) > 0) {
			ra.addFlashAttribute("success", messageSource.getMessage("updateSuccess", null, Locale.getDefault()));
		} else {
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
		}
		return "redirect:/" + URLConstant.URL_ADMIN_USER_REDIRECT;
	}

	@GetMapping(URLConstant.URL_ADMIN_USER_CHANGE_PASSWORD)
	public String changePassword(@PathVariable int id, Model model, RedirectAttributes ra) {
		User user = userService.findById(id);
		if (user == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noData", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_ADMIN_USER_REDIRECT;
		}
		model.addAttribute("user", user);
		return ViewNameConstant.ADMIN_USER_CHANGE_PASSWORD;
	}

	@PostMapping(URLConstant.URL_ADMIN_USER_CHANGE_PASSWORD)
	public String changePassword(@Valid @ModelAttribute("userError") User user, BindingResult rs,
			@RequestParam String oldPassword, @RequestParam String confirmPassword, Model model,
			RedirectAttributes ra) {
		model.addAttribute("user", user);
		User oldUser = userService.findById(user.getId());
		userValidate.validatePassword(user, rs, oldUser, confirmPassword, oldPassword, model);
		if (rs.hasFieldErrors("password")) {
			return ViewNameConstant.ADMIN_USER_CHANGE_PASSWORD;
		}
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		if (userService.updatePassword(user) > 0) {
			ra.addFlashAttribute("success",
					messageSource.getMessage("changePasswordSuccess", null, Locale.getDefault()));
		} else {
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
		}
		return "redirect:/" + URLConstant.URL_ADMIN_USER_REDIRECT;
	}

	@GetMapping(URLConstant.URL_ADMIN_USER_DEL)
	public String del(@PathVariable int id, RedirectAttributes ra) {
		User user = userService.findById(id);
		if (user == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noData", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_ADMIN_USER_REDIRECT;
		}
		if (user.getRole().getId() == DatabaseConstant.ROLE_ADMIN) {
			ra.addFlashAttribute("error", messageSource.getMessage("notDelAdminError", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_ADMIN_USER_REDIRECT;
		}
		if (userService.del(id) > 0) {
			ra.addFlashAttribute("success", messageSource.getMessage("delSuccess", null, Locale.getDefault()));
		} else {
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
		}
		return "redirect:/" + URLConstant.URL_ADMIN_USER_REDIRECT;
	}

}
