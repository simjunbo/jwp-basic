package next.service;

import core.annotation.Inject;
import core.annotation.Service;
import next.dao.QuestionRepository;
import next.dao.UserRepository;

@Service
public class MyQnaService {
	private UserRepository userRepository;
	private QuestionRepository questionRepository;

	@Inject
	public MyQnaService(UserRepository userRepository, QuestionRepository questionRepository) {
		this.userRepository = userRepository;
		this.questionRepository = questionRepository;
	}
}
