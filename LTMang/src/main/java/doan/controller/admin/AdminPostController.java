package doan.controller.admin;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import doan.constant.DatabaseConstant;
import doan.constant.GlobalConstant;
import doan.constant.URLConstant;
import doan.constant.ViewNameConstant;
import doan.model.Post;
import doan.service.PostService;
import doan.util.PageUtil;
import doan.util.StringUtil;
import doan.util.bean.Status;

@Controller
@RequestMapping(URLConstant.URL_ADMIN)
public class AdminPostController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PostService postService;

	@GetMapping({ URLConstant.URL_ADMIN_POST, URLConstant.URL_ADMIN_POST_PAGINATION })
	public String index(@PathVariable(required = false) Integer page, Model model, RedirectAttributes ra) {
		int currentPage = GlobalConstant.DEFAULT_PAGE;
		if (page != null) {
			if (page < GlobalConstant.DEFAULT_PAGE) {
				ra.addFlashAttribute("error", messageSource.getMessage("pageError", null, Locale.getDefault()));
				return "redirect:/" + URLConstant.URL_ADMIN_POST_REDIRECT;
			}
			currentPage = page;
		}
		int offset = PageUtil.getOffset(currentPage);
		int totalRow = postService.totalRow();
		int totalPage = PageUtil.getTotalPage(totalRow);
		List<Post> listPost = postService.getList(offset, GlobalConstant.TOTAL_ROW);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("totalRow", totalRow);
		model.addAttribute("listPost", listPost);
		return ViewNameConstant.ADMIN_POST_INDEX;
	}

	@GetMapping(URLConstant.URL_ADMIN_POST_DETAIL)
	public String detail(@PathVariable int id, Model model, RedirectAttributes ra) {
		Post post = postService.findById(id);
		if (post == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noData", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_ADMIN_POST_REDIRECT;
		}
		model.addAttribute("post", post);
		return ViewNameConstant.ADMIN_POST_DETAIL;
	}

	// kiểm duyệt
	@PostMapping(value = URLConstant.URL_ADMIN_POST_CENSORED, produces = "application/json")
	@ResponseBody
	public String censored(@RequestParam int id) {
		Post post = postService.findById(id);
		if (post == null) {
			return new Gson().toJson(new Status(1, "Không có dữ liệu tương ứng"));
		}
		if (post.getCensored() == DatabaseConstant.POST_CENSORED) {
			return new Gson().toJson(new Status(1, "Bài viết đã được kiểm duyệt trước đó"));
		}
		post.setCensored(1);
		if (postService.updateCensored(post) > 0) {
			return new Gson().toJson(new Status(0, "Thành công, bài viết đã được duyệt"));
		}
		return new Gson().toJson(new Status(1, "Có lỗi xảy ra, vui lòng thử lại sau"));
	}

	@GetMapping({ URLConstant.URL_ADMIN_POST_SEARCH, URLConstant.URL_ADMIN_POST_SEARCH_2,
			URLConstant.URL_ADMIN_POST_SEARCH_PAGINATION_2 })
	public String search(@PathVariable(required = false) Integer page, Model model, RedirectAttributes ra,
			@RequestParam(required = false) String title, @PathVariable(required = false) String titleURL,
			@RequestParam(required = false) String username, @PathVariable(required = false) String usernameURL,
			@RequestParam(required = false) Integer censored, @PathVariable(required = false) Integer censoredURL) {
		if (titleURL != null) {
			title = StringUtil.dashToSpace(titleURL);
		}
		if (usernameURL != null) {
			username = usernameURL;
		}
		if (censoredURL != null) {
			censored = censoredURL;
		}
		if (title == null) {
			title = GlobalConstant.EMPTY;
		}
		if (username == null) {
			username = GlobalConstant.EMPTY;
		}
		if (censored == null) {
			censored = 2;
		}
		if (title.equals(GlobalConstant.EMPTY) && username.equals(GlobalConstant.EMPTY) && censored == 2) {
			ra.addFlashAttribute("error", messageSource.getMessage("searchError", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_ADMIN_POST_REDIRECT;
		}
		int currentPage = GlobalConstant.DEFAULT_PAGE;
		if (page != null) {
			if (page < GlobalConstant.DEFAULT_PAGE) {
				ra.addFlashAttribute("error", messageSource.getMessage("pageError", null, Locale.getDefault()));
				return "redirect:/" + URLConstant.URL_ADMIN_POST_REDIRECT;
			}
			currentPage = page;
		}
		int offset = PageUtil.getOffset(currentPage);
		int totalRow = postService.totalRowSearch(title, username, censored);
		int totalPage = PageUtil.getTotalPage(totalRow);
		List<Post> listPost = postService.search(title, username, censored, offset, GlobalConstant.TOTAL_ROW);
		model.addAttribute("title", title);
		model.addAttribute("username", username);
		model.addAttribute("censored", censored);
		model.addAttribute("search", "/tim-kiem");
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("totalRow", totalRow);
		model.addAttribute("listPost", listPost);
		return ViewNameConstant.ADMIN_POST_INDEX;
	}

}
