package next.service;

import com.google.common.collect.Lists;
import next.CannotDeleteException;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.verify;
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

	@Test
	public void deleteQuestion_삭제할수_있음() throws Exception {
		User user = newUser("userId");
		// 오버라이딩을 통해 테스트 통과.. 참 신기하네
		Question question = new Question(1L, user.getUserId(), "title", "contents", new Date(), 0) {
			@Override public boolean canDelete(User user, List<Answer> answers) throws CannotDeleteException {
				return true;
			}
		};
		when(questionDao.findById(1L)).thenReturn(question);

		qnaService.deleteQuestion(1L, newUser("userId"));
		verify(questionDao).delete(question.getQuestionId());
	}

	@Test(expected = CannotDeleteException.class)
	public void deleteQuestion_삭제할수_없음() throws Exception {
		User user = newUser("userId");
		Question question = new Question(1L, user.getUserId(), "title", "contents", new Date(), 0) {
			@Override public boolean canDelete(User user, List<Answer> answers) throws CannotDeleteException {
				throw new CannotDeleteException("삭제할 수 없음");
			}
		};
		when(questionDao.findById(1L)).thenReturn(question);

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
