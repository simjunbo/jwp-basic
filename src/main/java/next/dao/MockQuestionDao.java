package next.dao;

import com.google.common.collect.Maps;
import next.model.Question;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MockQuestionDao implements QuestionDao {
	private Map<Long, Question> answers = Maps.newHashMap();

	@Override public Question insert(Question question) {
		return answers.put(question.getQuestionId(), question);
	}

	@Override public List<Question> findAll() {
		return answers.values().stream().collect(Collectors.toList());
	}

	@Override public Question findById(long questionId) {
		return answers.get(questionId);
	}

	@Override public void update(Question question) {
		answers.put(question.getQuestionId(), question);
	}

	@Override public void delete(long questionId) {
		answers.remove(questionId);
	}

	@Override public void updateCountOfAnswer(long questionId) {

	}
}
