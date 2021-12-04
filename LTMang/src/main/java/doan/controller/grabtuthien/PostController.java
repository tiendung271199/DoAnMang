package doan.controller.grabtuthien;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
import doan.util.FileUtil;
import doan.util.PageUtil;
import doan.util.bean.Status;
import doan.validate.PostValidate;

@Controller
public class PostController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PostService postService;

	@Autowired
	private RegisterCharityService registerCharityService;

	@Autowired
	private PostValidate postValidate;

	@ModelAttribute
	public void getListPost(Model model) {
		model.addAttribute("listNew", postService.getListNew());
		model.addAttribute("listHighlight", postService.getListHighlight());
	}

	@GetMapping({ URLConstant.URL_POST_QUANLY, URLConstant.URL_POST_QUANLY_PAGINATION })
	public String quanLyPost(@PathVariable(required = false) Integer page, Model model, RedirectAttributes ra,
			HttpSession session) {
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noUserLogin", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_LOGIN;
		}
		if (userLogin.getRole().getId() != DatabaseConstant.ROLE_USER_1) {
			return "redirect:/error/403";
		}
		int currentPage = GlobalConstant.DEFAULT_PAGE;
		if (page != null) {
			if (page < GlobalConstant.DEFAULT_PAGE) {
				ra.addFlashAttribute("error", messageSource.getMessage("pageError", null, Locale.getDefault()));
				return "redirect:/" + URLConstant.URL_POST_QUANLY;
			}
			currentPage = page;
		}
		int offset = PageUtil.getOffset(currentPage);
		int totalRow = postService.totalRowByUserId(userLogin.getId());
		int totalPage = PageUtil.getTotalPage(totalRow);
		List<Post> listPostUserLogin = postService.findByUserId(userLogin.getId(), offset, GlobalConstant.TOTAL_ROW);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("totalRow", totalRow);
		model.addAttribute("listPostUserLogin", listPostUserLogin);
		return ViewNameConstant.QUANLY_POST;
	}

	@GetMapping(URLConstant.URL_POST_DETAIL)
	public String postDetail(@PathVariable int id, Model model, RedirectAttributes ra, HttpSession session) {
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noUserLogin", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_LOGIN;
		}
		if (userLogin.getRole().getId() != DatabaseConstant.ROLE_USER_1) {
			return "redirect:/error/403";
		}
		Post post = postService.findById(id);
		if (post.getUser().getId() != userLogin.getId()) {
			ra.addFlashAttribute("error", messageSource.getMessage("noData", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_POST_QUANLY;
		}
		List<RegisterCharity> listRegisterCharity = registerCharityService.getListByPostId(id);
		model.addAttribute("post", post);
		model.addAttribute("listRegisterCharity", listRegisterCharity);
		return ViewNameConstant.POST_DETAIL;
	}

	// xử lý ngừng nhận hỗ trợ
	@PostMapping(value = URLConstant.URL_POST_STOP, produces = "application/json")
	@ResponseBody
	public String confirmStopCharity(@RequestParam int id, HttpSession session) {
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			return new Gson().toJson(new Status(1, "Chưa đăng nhập"));
		}
		if (userLogin.getRole().getId() != DatabaseConstant.ROLE_USER_1) {
			return new Gson().toJson(new Status(1, "Không có quyền"));
		}
		Post post = postService.findById(id);
		if (post == null) {
			return new Gson().toJson(new Status(1, "Không có dữ liệu tương ứng"));
		}
		if (post.getUser().getId() != userLogin.getId()) {
			return new Gson().toJson(new Status(1, "Không có dữ liệu tương ứng"));
		}
		System.out.println("Status trước: " + post.getStatus());
		post.setStatus(DatabaseConstant.POST_STATUS_2);
		System.out.println("Status sau: " + post.getStatus());
		if (postService.updateStatus(post) > 0) {
			return new Gson().toJson(
					new Status(0, "Xác nhận ngừng nhận giúp đỡ thành công\nBài viết này không còn nhận giúp đỡ nữa"));
		}
		return new Gson().toJson(new Status(1, "Có lỗi xảy ra, vui lòng thử lại sau"));
	}

	@GetMapping(URLConstant.URL_POST_ADD)
	public String post(RedirectAttributes ra, HttpSession session) {
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noUserLogin", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_LOGIN;
		}
		if (userLogin.getRole().getId() != DatabaseConstant.ROLE_USER_1) {
			return "redirect:/error/403";
		}
		return ViewNameConstant.POST;
	}

	@PostMapping(URLConstant.URL_POST_ADD)
	public String post(@Valid @ModelAttribute("postError") Post post, BindingResult rs,
			@RequestParam("image") MultipartFile multipartFile, Model model, RedirectAttributes ra,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		model.addAttribute("post", post);
		postValidate.validateQuantity(post, rs);
		postValidate.validatePicture(multipartFile, rs);
		if (rs.hasErrors()) {
			return ViewNameConstant.POST;
		}
		String fileName = FileUtil.uploadFile(multipartFile, request);
		if (fileName.equals(GlobalConstant.EMPTY)) {
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_POST_ADD;
		}
		post.setPicture(fileName);
		post.setUser(userLogin);
		if (postService.save(post) > 0) {
			ra.addFlashAttribute("success", messageSource.getMessage("addPostSuccess", null, Locale.getDefault()));
		} else {
			FileUtil.delFile(fileName, request);
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
		}
		return "redirect:/" + URLConstant.URL_POST_QUANLY;
	}

	@GetMapping(URLConstant.URL_POST_UPDATE)
	public String postUpdate(@PathVariable int id, Model model, RedirectAttributes ra, HttpSession session) {
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noUserLogin", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_LOGIN;
		}
		if (userLogin.getRole().getId() != DatabaseConstant.ROLE_USER_1) {
			return "redirect:/error/403";
		}
		Post post = postService.findById(id);
		if (post == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noData", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_POST_QUANLY;
		}
		if (post.getUser().getId() != userLogin.getId()) {
			ra.addFlashAttribute("error", messageSource.getMessage("noData", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_POST_QUANLY;
		}
		model.addAttribute("post", post);
		return ViewNameConstant.POST_UPDATE;
	}

	@PostMapping(URLConstant.URL_POST_UPDATE)
	public String postUpdate(@Valid @ModelAttribute("postError") Post post, BindingResult rs,
			@RequestParam("image") MultipartFile multipartFile, Model model, RedirectAttributes ra,
			HttpServletRequest request) {
		Post oldPost = postService.findById(post.getId());
		model.addAttribute("post", post);
		postValidate.validatePictureUpdate(multipartFile, rs);
		if (rs.hasErrors()) {
			return ViewNameConstant.POST_UPDATE;
		}
		String fileName = FileUtil.uploadFile(multipartFile, request);
		if (fileName.equals(GlobalConstant.EMPTY)) {
			post.setPicture(oldPost.getPicture());
		} else {
			post.setPicture(fileName);
		}
		if (postService.update(post) > 0) {
			ra.addFlashAttribute("success", messageSource.getMessage("updatePostSuccess", null, Locale.getDefault()));
			if (!fileName.equals(GlobalConstant.EMPTY)) {
				FileUtil.delFile(oldPost.getPicture(), request);
			}
		} else {
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
			if (!fileName.equals(GlobalConstant.EMPTY)) {
				FileUtil.delFile(fileName, request);
			}
		}
		return "redirect:/" + URLConstant.URL_POST_QUANLY;
	}

	@GetMapping(URLConstant.URL_POST_DEL)
	public String postDel(@PathVariable int id, RedirectAttributes ra, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noUserLogin", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_LOGIN;
		}
		if (userLogin.getRole().getId() != DatabaseConstant.ROLE_USER_1) {
			return "redirect:/error/403";
		}
		Post post = postService.findById(id);
		if (post == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noData", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_POST_QUANLY;
		}
		if (post.getUser().getId() != userLogin.getId()) {
			ra.addFlashAttribute("error", messageSource.getMessage("noData", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_POST_QUANLY;
		}
		if (postService.del(id) > 0) {
			ra.addFlashAttribute("success", messageSource.getMessage("delPostSuccess", null, Locale.getDefault()));
			FileUtil.delFile(post.getPicture(), request);
		} else {
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
		}
		return "redirect:/" + URLConstant.URL_POST_QUANLY;
	}

}
