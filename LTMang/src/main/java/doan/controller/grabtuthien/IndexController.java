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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import doan.constant.DatabaseConstant;
import doan.constant.GlobalConstant;
import doan.constant.URLConstant;
import doan.constant.ViewNameConstant;
import doan.model.Post;
import doan.model.User;
import doan.service.PostService;
import doan.util.DateUtil;
import doan.util.MyUtil;
import doan.util.PageUtil;
import doan.util.StringUtil;

@Controller
public class IndexController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PostService postService;

	@ModelAttribute
	public void getListPost(Model model) {
		model.addAttribute("listNew", postService.getListNew());
		model.addAttribute("listHighlight", postService.getListHighlight());
	}

	@GetMapping({ URLConstant.URL_INDEX, URLConstant.URL_INDEX_2, URLConstant.URL_INDEX_PAGINATION,
			URLConstant.URL_SEARCH, URLConstant.URL_SEARCH_PAGINATION })
	public String index(@PathVariable(required = false) Integer page, Model model, RedirectAttributes ra,
			@RequestParam(required = false) String keyword, @PathVariable(required = false) String keywordURL) {
		int currentPage = GlobalConstant.DEFAULT_PAGE;
		if (page != null) {
			if (page < GlobalConstant.DEFAULT_PAGE) {
				ra.addFlashAttribute("error", messageSource.getMessage("pageError", null, Locale.getDefault()));
				return "redirect:/" + URLConstant.URL_INDEX;
			}
			currentPage = page;
		}
		int offset = PageUtil.getOffset(currentPage);
		int totalRow = postService.totalRowByCensored();
		int totalPage = PageUtil.getTotalPage(totalRow);
		List<Post> listPost = postService.getListByCensored(offset, GlobalConstant.TOTAL_ROW);
		if (keywordURL != null) {
			keyword = StringUtil.dashToSpace(keywordURL);
		}
		if (keyword != null) {
			model.addAttribute("keyword", keyword);
			totalRow = postService.totalRowSearch(keyword, GlobalConstant.EMPTY, 2);
			totalPage = PageUtil.getTotalPage(totalRow);
			listPost = postService.search(keyword, GlobalConstant.EMPTY, 2, offset, GlobalConstant.TOTAL_ROW);
		}
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("totalRow", totalRow);
		model.addAttribute("listPost", listPost);
		return ViewNameConstant.INDEX;
	}

	@GetMapping(URLConstant.URL_DETAIL)
	public String detail(@PathVariable int id, Model model, RedirectAttributes ra, HttpSession session) {
		Post post = postService.findById(id);
		if (post == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noData", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_INDEX;
		}
		if (post.getCensored() == DatabaseConstant.POST_NON_CENSORED) {
			ra.addFlashAttribute("error", messageSource.getMessage("noData", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_INDEX;
		}
		int userId = 0;
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin != null) {
			userId = userLogin.getId();
		}
		String ipAddress = MyUtil.getIpAddress();
		StringBuilder sb = new StringBuilder();
		sb.append("views").append(id).append(userId).append(ipAddress);
		boolean check = false;
		if (session.getAttribute(sb.toString()) != null) {
			String date = (String) session.getAttribute(sb.toString());
			if (DateUtil.checkDateTime(date)) {
				check = true;
			}
		} else {
			check = true;
		}
		if (check) {
			if (postService.updateViews(id) > 0) {
				session.setAttribute(sb.toString(), DateUtil.getDateTime());
				post.setViews(post.getViews() + 1);
			}
		}
		String datePresent = DateUtil.findDatePresent();
		String datePast = DateUtil.findDatePast(datePresent, 7);
		List<Post> listPostOther = postService.getListByDate(id, DateUtil.convertFormatDate(datePresent) + " 23:59:59",
				DateUtil.convertFormatDate(datePast));
		model.addAttribute("post", post);
		model.addAttribute("listPostOther", listPostOther);
		return ViewNameConstant.DETAIL;
	}

}
