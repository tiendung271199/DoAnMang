package doan.controller.grabtuthien;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import doan.model.Reviews;
import doan.model.SurveyAnswer;
import doan.model.SurveyQuestion;
import doan.model.User;
import doan.service.PostService;
import doan.service.RegisterCharityService;
import doan.service.ReviewsService;
import doan.service.SurveyAnswerService;
import doan.service.SurveyQuestionService;
import doan.service.UserService;
import doan.util.PageUtil;
import doan.util.bean.ReviewsUtil;
import doan.util.bean.Status;

@Controller
public class ReviewsController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;

	@Autowired
	private RegisterCharityService registerCharityService;

	@Autowired
	private SurveyQuestionService surveyQuestionService;

	@Autowired
	private SurveyAnswerService surveyAnswerService;

	@Autowired
	private ReviewsService reviewsService;

	@ModelAttribute
	public void getListPost(Model model) {
		model.addAttribute("listNew", postService.getListNew());
		model.addAttribute("listHighlight", postService.getListHighlight());
	}

	@GetMapping(URLConstant.URL_REVIEWS_USER)
	public String reviews(@PathVariable int id, Model model, RedirectAttributes ra, HttpSession session) {
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noUserLogin", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_LOGIN;
		}
		RegisterCharity registerCharity = registerCharityService.findById(id);
		if (registerCharity == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noData", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_INDEX;
		}
		if (userLogin.getRole().getId() == DatabaseConstant.ROLE_USER_1) {
			if (!registerCharityService.checkCharity(registerCharity.getUser().getId(),
					registerCharity.getPost().getId())) {
				ra.addFlashAttribute("error", messageSource.getMessage("noData", null, Locale.getDefault()));
				return "redirect:/" + URLConstant.URL_POST_QUANLY;
			} else {
				if (!postService.checkPostByUser(userLogin.getId(), registerCharity.getPost().getId())) {
					ra.addFlashAttribute("error", messageSource.getMessage("noData", null, Locale.getDefault()));
					return "redirect:/" + URLConstant.URL_POST_QUANLY;
				}
			}
		} else {
			if (!registerCharityService.checkCharity(userLogin.getId(), registerCharity.getPost().getId())) {
				ra.addFlashAttribute("error", messageSource.getMessage("noData", null, Locale.getDefault()));
				return "redirect:/" + URLConstant.URL_POST_REGISTER_CHARITY_USER;
			}
		}
		List<SurveyQuestion> listQuestion = null;
		if (userLogin.getRole().getId() == DatabaseConstant.ROLE_USER_1) {
			model.addAttribute("user", registerCharity.getUser());
			listQuestion = surveyQuestionService.findByObject(3);
		} else {
			model.addAttribute("user", registerCharity.getPost().getUser());
			listQuestion = surveyQuestionService.findByObject(2);
		}
		model.addAttribute("registerCharity", registerCharity);
		model.addAttribute("listQuestion", listQuestion);
		return ViewNameConstant.REVIEWS_USER;
	}

	@Transactional
	@PostMapping(value = URLConstant.URL_REVIEWS_XULY, produces = "application/json")
	@ResponseBody
	public String xuLyReviews(@RequestParam int id, @RequestParam String q1, @RequestParam String q2,
			@RequestParam String q3, @RequestParam String other, Model model, RedirectAttributes ra,
			HttpSession session) {
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			return new Gson().toJson(new Status(1, "Chưa đăng nhập"));
		}
		RegisterCharity registerCharity = registerCharityService.findById(id);
		if (registerCharity == null) {
			return new Gson().toJson(new Status(1, "Không có dữ liệu tương ứng"));
		}
		User userDuocReviews = null;
		Post post = registerCharity.getPost();
		if (userLogin.getRole().getId() == DatabaseConstant.ROLE_USER_1) {
			userDuocReviews = registerCharity.getUser();
		} else {
			userDuocReviews = registerCharity.getPost().getUser();
		}
		Reviews reviews = new Reviews(userLogin, userDuocReviews, post, other);
		if (reviewsService.save(reviews) > 0) {
			reviews = reviewsService.getNewReviews();
		} else {
			return new Gson().toJson(new Status(1, "Có lỗi xảy ra, vui lòng thử lại sau"));
		}
		String[] arrQ1 = q1.split("\\-");
		String[] arrQ2 = q2.split("\\-");
		String[] arrQ3 = q3.split("\\-");
		SurveyAnswer sa1 = new SurveyAnswer(reviews, new SurveyQuestion(Integer.parseInt(arrQ1[0])), arrQ1[1]);
		SurveyAnswer sa2 = new SurveyAnswer(reviews, new SurveyQuestion(Integer.parseInt(arrQ2[0])), arrQ2[1]);
		SurveyAnswer sa3 = new SurveyAnswer(reviews, new SurveyQuestion(Integer.parseInt(arrQ3[0])), arrQ3[1]);
		List<SurveyAnswer> listAnswer = new ArrayList<SurveyAnswer>();
		listAnswer.add(sa1);
		listAnswer.add(sa2);
		listAnswer.add(sa3);
		if (surveyAnswerService.save(listAnswer)) {
			return new Gson().toJson(new Status(0, "Đánh giá người dùng thành công"));
		}
		return new Gson().toJson(new Status(1, "Có lỗi xảy ra, vui lòng thử lại sau"));
	}

	@GetMapping({ URLConstant.URL_INFO_USER, URLConstant.URL_INFO_USER_PAGINATION })
	public String info(@PathVariable int id, @PathVariable(required = false) Integer page, Model model,
			RedirectAttributes ra, HttpSession session) {
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noUserLogin", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_LOGIN;
		}
		int currentPage = GlobalConstant.DEFAULT_PAGE;
		if (page != null) {
			if (page < GlobalConstant.DEFAULT_PAGE) {
				ra.addFlashAttribute("error", messageSource.getMessage("pageError", null, Locale.getDefault()));
				return "redirect:/tai-khoan/thong-tin-nguoi-dung-" + id;
			}
			currentPage = page;
		}
		User user = userService.findById(id);
		if (user == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noData", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.URL_INDEX;
		}
		if (user.getRole().getId() == DatabaseConstant.ROLE_USER_1) {
			model.addAttribute("totalPost", postService.totalRowByUserIdAndCensored(id));
		}
		if (user.getRole().getId() == DatabaseConstant.ROLE_USER_2) {
			model.addAttribute("totalRegisterCharity", registerCharityService.totalRowByUserId(id));
			model.addAttribute("totalConfirmCharity", registerCharityService.totalRowConfirmByUserId(id));
		}
		List<ReviewsUtil> list = new ArrayList<ReviewsUtil>();
		int offset = PageUtil.getOffset(currentPage);
		int totalRow = reviewsService.totalRowByUserDuocReviews(user.getId());
		int totalPage = PageUtil.getTotalPage(totalRow);
		List<Reviews> listReviews = reviewsService.findByUserDuocReviews(user.getId(), offset,
				GlobalConstant.TOTAL_ROW);
		for (Reviews reviews : listReviews) {
			List<SurveyAnswer> listSurveyAnswer = surveyAnswerService.findByReviewsId(reviews.getId());
			list.add(new ReviewsUtil(reviews.getId(), reviews.getUserReviews(), reviews.getUserDuocReviews(),
					reviews.getPost(), reviews.getOther(), reviews.getCreateAt(), listSurveyAnswer));
		}
		model.addAttribute("user", user);
		model.addAttribute("listReviews", list);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		return ViewNameConstant.INFO_USER;
	}

}
