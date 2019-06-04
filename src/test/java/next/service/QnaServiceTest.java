package next.service;

import com.google.common.collect.Lists;
import next.CannotDeleteException;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QnaServiceTest {
	@Mock
	private QuestionDao questionDao;

	@Mock
	private AnswerDao answerDao;

	@Mock
	private QnaService qnaService;

	@Before
	public void setup() {
		qnaService = new QnaService(questionDao, answerDao);
	}

	@Test(expected = CannotDeleteException.class)
	public void deleteQuestion_없는_질문() throws Exception {
		when(questionDao.findById(1L)).thenReturn(null);

		qnaService.deleteQuestion(1L, newUser("userId"));
	}

	@Test(expected = CannotDeleteException.class)
	public void deleteQuestion_다른_사용자() throws Exception {
		Question question = newQuestion(1L, "javajigi");
		when(questionDao.findById(1L)).thenReturn(question);
		qnaService.deleteQuestion(1L, newUser("userId"));
	}

	@Test
	public void deleteQuestion_같은_사용자_답변없음() throws Exception {
		Question question = newQuestion(1L, "javajigi");
		when(questionDao.findById(1L)).thenReturn(question);
		when(answerDao.findAllByQuestionId(1L)).thenReturn(Lists.newArrayList());

		qnaService.deleteQuestion(1L, newUser("javajigi"));
	}

	private Question newQuestion(long questionId, String writer) {
		return new Question(questionId, writer, "test", "test", null, 0);
	}

	private User newUser(String userId) {
		return new User(userId, "test", "test", "test");
	}
}
