package doan.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import doan.constant.URLConstant;
import doan.model.SurveyQuestion;
import doan.service.SurveyQuestionService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReviewsAPIController {

	@Autowired
	private SurveyQuestionService surveyQuestionService;

	@GetMapping(URLConstant.URL_QUESTION_API)
	public List<SurveyQuestion> getListByObject(@PathVariable int object) {
		return surveyQuestionService.findByObject(object);
	}

}
