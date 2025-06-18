package com.example.question_service.service;


import com.example.question_service.dao.QuestionDao;
import com.example.question_service.model.Question;
import com.example.question_service.model.QuestionWrapper;
import com.example.question_service.model.Response;
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

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
        List<Integer> questionsIds = repo.findRandomQuestionsByCategory(categoryName, numQuestions);
        return new ResponseEntity<>(questionsIds, HttpStatus.OK);
    }


    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers=new ArrayList<>();

        for(Integer id:questionIds){
            Question quest= repo.getById(id);
            QuestionWrapper wrap=new QuestionWrapper(quest.getId(), quest.getQuestionTitle(), quest.getOption1(), quest.getOption2(), quest.getOption3(), quest.getOption4());
            wrappers.add(wrap);
        }
        return new ResponseEntity<>(wrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        int i=0, right=0;
        for(Response response:responses){
            Question question=repo.findById(response.getId()).get();
            if(response.getResponse().equals(question.getRightAnswer())){
                right++;
            }
            i++;
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
