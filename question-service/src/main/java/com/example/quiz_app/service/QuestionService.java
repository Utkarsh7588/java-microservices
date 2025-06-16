package com.example.quiz_app.service;

import com.example.quiz_app.dao.QuestionDao;
import com.example.quiz_app.model.Question;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    QuestionDao repo;
    public QuestionService(QuestionDao repo){
        this.repo=repo;
    }
    public List<Question> getQuestions() {
        return repo.findAll();
    }
    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(repo.findByCategory(category),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> addQuestion(Question question) {
        repo.save(question);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<Question>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
        List<Question> questions = repo.findRandomQuestionsByCategory(categoryName, numQuestions);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }



}
