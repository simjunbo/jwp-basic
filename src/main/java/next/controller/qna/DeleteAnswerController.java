package next.controller.qna;

import core.jdbc.DataAccessException;
import core.mvc.Controller;
import core.mvc.JsonView;
import core.mvc.View;
import next.dao.AnswerDao;
import next.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteAnswerController implements Controller {
	@Override
	public View execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Long answerId = Long.parseLong(req.getParameter("answerId"));
		AnswerDao answerDao = new AnswerDao();

		try {
			answerDao.delete(answerId);
			req.setAttribute("result", Result.ok());
		} catch (DataAccessException e) {
			req.setAttribute("result", Result.fail(e.getMessage()));
		}

		return new JsonView();
	}
}
