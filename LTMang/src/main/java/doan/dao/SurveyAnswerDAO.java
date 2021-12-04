package doan.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import doan.model.Reviews;
import doan.model.SurveyAnswer;
import doan.model.SurveyQuestion;

@Repository
public class SurveyAnswerDAO extends AbstractDAO<SurveyAnswer> {

	public List<SurveyAnswer> findByReviewsId(int reviewsId) {
		String sql = "SELECT * FROM survey_answer sa INNER JOIN survey_question sq ON sa.sqId = sq.id WHERE reviewsId = ?";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<SurveyAnswer>>() {
			List<SurveyAnswer> list = new ArrayList<SurveyAnswer>();

			@Override
			public List<SurveyAnswer> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new SurveyAnswer(rs.getInt("sa.id"), new Reviews(rs.getInt("reviewsId")),
							new SurveyQuestion(rs.getInt("sq.id"), rs.getString("sq.question"), rs.getInt("sq.object")),
							rs.getString("answer"), rs.getTimestamp("sa.createAt")));
				}
				return list;
			}

		}, reviewsId);
	}

	@Override
	public int save(SurveyAnswer surveyAnswer) {
		String sql = "INSERT INTO survey_answer(reviewsId,sqId,answer) VALUES (?,?,?)";
		return jdbcTemplate.update(sql, surveyAnswer.getReviews().getId(), surveyAnswer.getQuestion().getId(),
				surveyAnswer.getAnswer());
	}

}
