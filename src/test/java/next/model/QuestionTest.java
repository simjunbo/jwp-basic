package next.model;

import next.CannotDeleteException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class QuestionTest {
	public static Question newQuestion(String writer) {
		return new Question(1L, writer, "title", "contents", new Date(), 0);
	}

	public static Question newQuestion(long questionId, String writer) {
		return new Question(questionId, writer, "title", "contetns", new Date(), 0);
	}

	@Test(expected = CannotDeleteException.class)
	public void canDelete_글쓴이_다르다() throws Exception {
		User user = newUser("javajigi");
		Question question = newQuestion("sanjigi");
		question.canDelete(user, new ArrayList<Answer>());
	}

	@Test
	public void canDelete_글쓴이_같음_답변_없음() throws Exception {
		User user = newUser("javajigi");
		Question question = newQuestion("javajigi");
		assertTrue(question.canDelete(user, new ArrayList<Answer>()));
	}

	@Test
	public void canDelete_같은_사용자_답변() throws Exception {
		String userId = "javajigi;";
		User user = newUser(userId);
		Question question = newQuestion(userId);
		List<Answer> answers = Arrays.asList(newAnswer(userId));
		assertTrue(question.canDelete(user, answers));
	}

	@Test(expected = CannotDeleteException.class)
	public void canDelete_다른_사용자_답변() throws Exception {
		String userId = "javajigi";
		List<Answer> answers = Arrays.asList(newAnswer(userId), newAnswer("sanjigi"));
		newQuestion(userId).canDelete(newUser(userId), answers);
	}

	private Answer newAnswer(String userId) {
		return new Answer(userId, "test", 1L);
	}

	private User newUser(String userId) {
		return new User(userId, "test", "test", "test");
	}
}
