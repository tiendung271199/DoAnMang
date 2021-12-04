package doan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doan.dao.SurveyAnswerDAO;
import doan.model.SurveyAnswer;

@Service
public class SurveyAnswerService {

	@Autowired
	private SurveyAnswerDAO surveyAnswerDAO;

	public List<SurveyAnswer> findByReviewsId(int reviewsId) {
		return surveyAnswerDAO.findByReviewsId(reviewsId);
	}

	public int save(SurveyAnswer surveyAnswer) {
		return surveyAnswerDAO.save(surveyAnswer);
	}

	public boolean save(List<SurveyAnswer> list) {
		if (list.size() > 0) {
			for (SurveyAnswer surveyAnswer : list) {
				if (surveyAnswerDAO.save(surveyAnswer) > 0) {
					System.out.print("");
				} else {
					return false;
				}
			}
		}
		return true;
	}

}
