package doan.controller.admin;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import doan.constant.GlobalConstant;
import doan.constant.URLConstant;
import doan.constant.ViewNameConstant;
import doan.model.Reviews;
import doan.model.SurveyAnswer;
import doan.service.ReviewsService;
import doan.service.SurveyAnswerService;
import doan.util.PageUtil;

@Controller
@RequestMapping(URLConstant.URL_ADMIN)
public class AdminReviewsController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ReviewsService reviewsService;

	@Autowired
	private SurveyAnswerService surveyAnswerService;

	@GetMapping({ URLConstant.URL_ADMIN_REVIEWS, URLConstant.URL_ADMIN_REVIEWS_PAGINATION })
	public String index(@PathVariable(required = false) Integer page, Model model, RedirectAttributes ra) {
		int currentPage = GlobalConstant.DEFAULT_PAGE;
		if (page != null) {
			if (page < GlobalConstant.DEFAULT_PAGE) {
				ra.addFlashAttribute("error", messageSource.getMessage("pageError", null, Locale.getDefault()));
				return "redirect:/" + URLConstant.URL_ADMIN_REVIEWS_REDIRECT;
			}
			currentPage = page;
		}
		int offset = PageUtil.getOffset(currentPage);
		int totalRow = reviewsService.totalRow();
		int totalPage = PageUtil.getTotalPage(totalRow);
		List<Reviews> listReviews = reviewsService.getList(offset, GlobalConstant.TOTAL_ROW);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("totalRow", totalRow);
		model.addAttribute("listReviews", listReviews);
		return ViewNameConstant.ADMIN_REVIEWS_INDEX;
	}

	@GetMapping(URLConstant.URL_ADMIN_REVIEWS_DETAIL)
	public String detail(@PathVariable int id, Model model, RedirectAttributes ra) {
		Reviews reviews = reviewsService.findById(id);
		if (reviews == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noData", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_ADMIN_REVIEWS_REDIRECT;
		}
		List<SurveyAnswer> listAnswer = surveyAnswerService.findByReviewsId(id);
		model.addAttribute("reviews", reviews);
		model.addAttribute("listAnswer", listAnswer);
		return ViewNameConstant.ADMIN_REVIEWS_DETAIL;
	}

}
