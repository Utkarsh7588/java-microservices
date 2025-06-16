package com.example.quiz_app.service;

import com.example.quiz_app.dao.QuestionDao;
import com.example.quiz_app.dao.QuizDao;
import com.example.quiz_app.model.Question;
import com.example.quiz_app.model.QuestionWrapper;
import com.example.quiz_app.model.Quiz;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    QuestionDao questionDao;
    QuizDao quizDao;

    public QuizService(QuestionDao questionDao, QuizDao quizDao) {
        this.questionDao = questionDao;
        this.quizDao = quizDao;
    }

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        List<Question> list= questionDao.findRandomQuestionsByCategory(category, numQ);
        quiz.setQuestions(list);
        quizDao.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz=quizDao.findById(id);
        List<Question> questionsFromDB=quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser=new ArrayList<>();
        for(Question q: questionsFromDB){
            QuestionWrapper qw=new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(), q.getOption3(), q.getOption4());
        questionsForUser.add(qw);
        }
        return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
    }
}
