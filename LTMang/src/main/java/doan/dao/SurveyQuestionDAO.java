package doan.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import doan.model.SurveyQuestion;

@Repository
public class SurveyQuestionDAO extends AbstractDAO<SurveyQuestion> {

	public List<SurveyQuestion> findByObject(int object) {
		String sql = "SELECT * FROM survey_question WHERE object = ?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SurveyQuestion.class), object);
	}

}
