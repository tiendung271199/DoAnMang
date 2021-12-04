package doan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doan.dao.SurveyQuestionDAO;
import doan.model.SurveyQuestion;

@Service
public class SurveyQuestionService {

	@Autowired
	private SurveyQuestionDAO surveyQuestionDAO;

	public List<SurveyQuestion> findByObject(int object) {
		return surveyQuestionDAO.findByObject(object);
	}

}
