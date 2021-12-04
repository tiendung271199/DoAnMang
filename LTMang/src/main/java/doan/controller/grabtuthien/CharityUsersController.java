package doan.controller.grabtuthien;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import doan.constant.DatabaseConstant;
import doan.constant.GlobalConstant;
import doan.constant.URLConstant;
import doan.constant.ViewNameConstant;
import doan.model.Post;
import doan.model.RegisterCharity;
import doan.model.User;
import doan.service.PostService;
import doan.service.RegisterCharityService;
import doan.util.PageUtil;
import doan.util.RegisterCharityUtil;
import doan.util.bean.Status;

@Controller
public class CharityUsersController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PostService postService;

	@Autowired
	private RegisterCharityService registerCharityService;

	@Autowired
	private RegisterCharityUtil registerCharityUtil;

	@ModelAttribute
	public void getListPost(Model model) {
		model.addAttribute("listNew", postService.getListNew());
		model.addAttribute("listHighlight", postService.getListHighlight());
	}

	// register charity
	@PostMapping(value = URLConstant.URL_POST_REGISTER_CHARITY, produces = "application/json")
	@ResponseBody
	public String registerCharity(@RequestParam int id, HttpSession session) {
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			return new Gson().toJson(new Status(1, "Chưa đăng nhập"));
		}
		if (userLogin.getRole().getId() != DatabaseConstant.ROLE_USER_2) {
			return new Gson().toJson(new Status(1, "Không có quyền"));
		}
		Post post = postService.findById(id);
		if (post == null) {
			return new Gson().toJson(new Status(1, "Không có dữ liệu tương ứng"));
		}
		if (post.getCensored() == DatabaseConstant.POST_NON_CENSORED) {
			return new Gson().toJson(new Status(1, "Không có dữ liệu tương ứng"));
		}
		if (post.getStatus() == DatabaseConstant.POST_STATUS_2) {
			return new Gson().toJson(new Status(1, "Đối tượng này không còn nhận hỗ trợ nữa"));
		}
		int totalRowByPostId = registerCharityService.totalRowByPostId(id);
		if (totalRowByPostId >= 10) {
			return new Gson().toJson(
					new Status(1, "Không đăng ký được vì đối tượng đã đủ số lượng nhà hảo tâm đăng ký giúp đỡ"));
		}
		RegisterCharity registerCharity = registerCharityService.findByPostIdAndUserId(id, userLogin.getId());
		if (registerCharity != null) {
			registerCharity.setStatus(1);
			if (registerCharityService.updateStatus(registerCharity) > 0) {
				return new Gson().toJson(new Status(0, "Đăng ký giúp đỡ thành công"));
			}
		} else {
			if (registerCharityService.save(new RegisterCharity(userLogin, post)) > 0) {
				return new Gson().toJson(new Status(0, "Đăng ký giúp đỡ thành công"));
			}
		}
		return new Gson().toJson(new Status(1, "Có lỗi xảy ra, vui lòng thử lại sau"));
	}

	// cancel register charity
	@PostMapping(value = URLConstant.URL_POST_CANCEL_REGISTER_CHARITY, produces = "application/json")
	@ResponseBody
	public String cancelRegisterCharity(@RequestParam int id, HttpSession session) {
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			return new Gson().toJson(new Status(1, "Chưa đăng nhập"));
		}
		if (userLogin.getRole().getId() != DatabaseConstant.ROLE_USER_2) {
			return new Gson().toJson(new Status(1, "Không có quyền"));
		}
		Post post = postService.findById(id);
		if (post == null) {
			return new Gson().toJson(new Status(1, "Không có dữ liệu tương ứng"));
		}
		RegisterCharity registerCharity = registerCharityService.findByPostIdAndUserId(id, userLogin.getId());
		if (registerCharity != null) {
			if (registerCharity.getConfirm() == DatabaseConstant.REGISTER_CONFIRM) {
				return new Gson().toJson(new Status(1, "Hai bên đã xác nhận giúp đỡ, không thể huỷ"));
			}
			registerCharity.setStatus(0);
			if (registerCharityService.updateStatus(registerCharity) > 0) {
				return new Gson().toJson(new Status(0, "Huỷ đăng ký giúp đỡ thành công"));
			}
		}
		return new Gson().toJson(new Status(1, "Có lỗi xảy ra, vui lòng thử lại sau"));
	}

	// check register
	@PostMapping("check-register")
	@ResponseBody
	public String check(@RequestParam int id, HttpSession session) {
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			return "2";
		}
		if (userLogin.getRole().getId() != DatabaseConstant.ROLE_USER_2) {
			return "2";
		}
		if (registerCharityUtil.checkRegister(id, userLogin.getId())) {
			return "1";
		}
		return "0";
	}

	// confirm register charity
	@PostMapping(value = URLConstant.URL_POST_CONFIRM_REGISTER_CHARITY, produces = "application/json")
	@ResponseBody
	public String confirmRegisterCharity(@RequestParam int id, HttpSession session) {
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			return new Gson().toJson(new Status(1, "Chưa đăng nhập"));
		}
		if (userLogin.getRole().getId() != DatabaseConstant.ROLE_USER_1) {
			return new Gson().toJson(new Status(1, "Không có quyền"));
		}
		RegisterCharity registerCharity = registerCharityService.findById(id);
		if (registerCharity == null) {
			return new Gson().toJson(new Status(1, "Không có dữ liệu tương ứng"));
		}
		Post post = postService.findById(registerCharity.getPost().getId());
		// 1 nhà hảo tâm => chỉ xác nhận 1 người duy nhất
		if (post.getQuantity() == 1) {
			if (registerCharityService.totalRowConfirmByPostId(post.getId()) == 1) {
				return new Gson()
						.toJson(new Status(1, "Không xác nhận được vì bạn đã xác nhận đủ số lượng nhà hảo tâm"));
			}
		}
		registerCharity.setConfirm(1);
		if (registerCharityService.updateConfirm(registerCharity) > 0) {
			return new Gson().toJson(new Status(0,
					"Xác nhận đăng ký giúp đỡ thành công\nHãy liên lạc với nhà hảo tâm hoặc nhà hảo tâm sẽ liên lạc với bạn"));
		}
		return new Gson().toJson(new Status(1, "Có lỗi xảy ra, vui lòng thử lại sau"));
	}

	// danh sách bài viết đã đăng ký giúp đỡ của user (role = 3)
	@GetMapping({ URLConstant.URL_POST_REGISTER_CHARITY_USER, URLConstant.URL_POST_REGISTER_CHARITY_USER_PAGINATION })
	public String postRegister(@PathVariable(required = false) Integer page, Model model, RedirectAttributes ra,
			HttpSession session) {
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noUserLogin", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_LOGIN;
		}
		if (userLogin.getRole().getId() != DatabaseConstant.ROLE_USER_2) {
			return "redirect:/error/403";
		}
		int currentPage = GlobalConstant.DEFAULT_PAGE;
		if (page != null) {
			if (page < GlobalConstant.DEFAULT_PAGE) {
				ra.addFlashAttribute("error", messageSource.getMessage("pageError", null, Locale.getDefault()));
				return "redirect:/" + URLConstant.URL_POST_REGISTER_CHARITY_USER;
			}
			currentPage = page;
		}
		int offset = PageUtil.getOffset(currentPage);
		int totalRow = registerCharityService.totalRowByUserId(userLogin.getId());
		int totalPage = PageUtil.getTotalPage(totalRow);
		List<RegisterCharity> listRegisterCharity = registerCharityService.getListByUserId(userLogin.getId(), offset,
				GlobalConstant.TOTAL_ROW);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("totalRow", totalRow);
		model.addAttribute("listRegisterCharity", listRegisterCharity);
		return ViewNameConstant.POST_REGISTER_USER;
	}

}
