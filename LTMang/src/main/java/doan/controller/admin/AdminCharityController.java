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
import doan.model.RegisterCharity;
import doan.service.RegisterCharityService;
import doan.util.PageUtil;

@Controller
@RequestMapping(URLConstant.URL_ADMIN)
public class AdminCharityController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private RegisterCharityService registerCharityService;

	@GetMapping({ URLConstant.URL_ADMIN_CHARITY, URLConstant.URL_ADMIN_CHARITY_PAGINATION })
	public String index(@PathVariable(required = false) Integer page, Model model, RedirectAttributes ra) {
		int currentPage = GlobalConstant.DEFAULT_PAGE;
		if (page != null) {
			if (page < GlobalConstant.DEFAULT_PAGE) {
				ra.addFlashAttribute("error", messageSource.getMessage("pageError", null, Locale.getDefault()));
				return "redirect:/" + URLConstant.URL_ADMIN_CHARITY_REDIRECT;
			}
			currentPage = page;
		}
		int offset = PageUtil.getOffset(currentPage);
		int totalRow = registerCharityService.totalRow();
		int totalPage = PageUtil.getTotalPage(totalRow);
		List<RegisterCharity> listCharity = registerCharityService.getList(offset, GlobalConstant.TOTAL_ROW);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("totalRow", totalRow);
		model.addAttribute("listCharity", listCharity);
		return ViewNameConstant.ADMIN_CHARITY_INDEX;
	}

}
