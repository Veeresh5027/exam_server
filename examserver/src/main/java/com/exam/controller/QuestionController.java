package com.exam.controller;

import com.exam.Service.QuestionService;
import com.exam.Service.QuizService;
import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;

    //add question
    @PostMapping("/")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        return ResponseEntity.ok(this.questionService.addQuestion(question));
    }

    //update question
    @PutMapping("/")    
    public ResponseEntity<Question> updateQuestion(@RequestBody Question question) {
        return ResponseEntity.ok(this.questionService.updateQuestion(question));
    }

    //get all questions of quiz
    @GetMapping("/quiz/{qid}")
    public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("qid") Long qid) {
        Quiz quiz = this.quizService.getQuiz(qid);
        quiz.setQid(qid);
        Set<Question> questionsOfQuiz = quiz.getQuestions();

        // Hide answer for NORMAL users
        questionsOfQuiz.forEach(question -> question.setAnswer(""));

        return ResponseEntity.ok(questionsOfQuiz);
    }

    //get all questions of quiz
    @GetMapping("/quiz/all/{qid}")
    public ResponseEntity<?> getQuestionsOfQuizAdmin(@PathVariable("qid") Long qid) {
        Quiz quiz = this.quizService.getQuiz(qid);
        Set<Question> questions = quiz.getQuestions();
        List list = new ArrayList(questions);

        Collections.shuffle(list);
        return ResponseEntity.ok(list);
    }

    //get single question
    @GetMapping("/{quesId}")
    public Question get(@PathVariable("quesId") Long quesId) {
        return this.questionService.getQuestion(quesId);
    }

    //delete question
    @DeleteMapping("/{quesId}")
        public void delete(@PathVariable("quesId") Long quesId) {
        this.questionService.deleteQuestion(quesId);
    }

    //evaluate quiz
    @PostMapping("/eval-quiz")
    public ResponseEntity<?> evalQuiz(@RequestBody List<Question> questions) {
        System.out.println(questions);

        int marksGot = 0;
        int correctAnswers = 0;
        int attempted = 0;

        if (questions == null || questions.isEmpty()) {
            return ResponseEntity.badRequest().body("No questions provided");
        }

        // Assuming all questions belong to the same quiz
        double maxMarks = questions.get(0).getQuiz().getMaxMarks();
        double marksSingle = maxMarks / questions.size();

        for (Question q : questions) {
            Question question = this.questionService.get(q.getQuesId());

            if (q.getGivenAnswer() != null && !q.getGivenAnswer().trim().equals("")) {
                attempted++;

                if (question.getAnswer().trim().equalsIgnoreCase(q.getGivenAnswer().trim())) {
                    correctAnswers++;
                    marksGot += (int) marksSingle;
                }
            }
        }

        Map<String, Integer> map = Map.of(
                "marksGot", marksGot,
                "correctAnswers", correctAnswers,
                "attempted", attempted
        );

        return ResponseEntity.ok(map);
    }


}
