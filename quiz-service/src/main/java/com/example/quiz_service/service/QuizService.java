package com.example.quiz_service.service;

import com.example.quiz_service.dao.QuizDao;
import com.example.quiz_service.feign.QuizInterface;
import com.example.quiz_service.model.Question;
import com.example.quiz_service.model.QuestionWrapper;
import com.example.quiz_service.model.Quiz;
import com.example.quiz_service.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    QuizDao quizDao;
    QuizInterface quizInterface;

    public QuizService(QuizDao quizDao, QuizInterface quizInterface) {
        this.quizDao = quizDao;
        this.quizInterface = quizInterface;
    }

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionsIds(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<QuestionWrapper> questionsForUser = quizInterface.getQuestionsFromId(quiz.get().getQuestionsIds()).getBody();
        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateScore(Integer id, List<Response> responses) {
        Integer right=quizInterface.getScore(responses).getBody();
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
