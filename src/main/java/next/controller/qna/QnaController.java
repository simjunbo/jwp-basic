package next.controller.qna;

import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.mvc.ModelAndView;
import core.nmvc.AbstractNewController;
import next.service.MyQnaService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QnaController extends AbstractNewController {
	private MyQnaService qnaService;

	@Inject
	public QnaController(MyQnaService qnaService) {
		this.qnaService = qnaService;
	}

	@RequestMapping("/questions")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return jspView("/qna/kist.jsp");
	}

}
