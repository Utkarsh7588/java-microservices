package com.example.quiz_app.controller;

import com.example.quiz_app.model.Question;
import com.example.quiz_app.model.QuestionWrapper;
import com.example.quiz_app.model.Response;
import com.example.quiz_app.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    QuizService quizService;
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam  int num, @RequestParam String title){
        return quizService.createQuiz(category,num, title);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> response){
        return quizService.calculateScore(id, response);
    }
}
