package com.exam.service.impl;

import com.exam.Service.impl.QuestionServiceImpl;
import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import com.exam.repo.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionServiceImplTest {

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Mock
    private QuestionRepository questionRepository;

    private Question question;
    private Quiz quiz;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        quiz = new Quiz();
        quiz.setQid(1L);

        question = new Question();
        question.setQuesId(101L);
        question.setContent("What is Java?");
        question.setQuiz(quiz);
    }

    @Test
    void testAddQuestion() {
        when(questionRepository.save(question)).thenReturn(question);

        Question result = questionService.addQuestion(question);

        assertEquals(question, result);
        verify(questionRepository, times(1)).save(question);
    }

    @Test
    void testUpdateQuestion() {
        when(questionRepository.save(question)).thenReturn(question);

        Question result = questionService.updateQuestion(question);

        assertEquals(question, result);
        verify(questionRepository, times(1)).save(question);
    }

    @Test
    void testGetQuestions() {
        List<Question> questionList = Arrays.asList(question);
        when(questionRepository.findAll()).thenReturn(questionList);

        Set<Question> result = questionService.getQuestions();

        assertEquals(1, result.size());
        assertTrue(result.contains(question));
        verify(questionRepository, times(1)).findAll();
    }

    @Test
    void testGetQuestionById() {
        when(questionRepository.findById(101L)).thenReturn(Optional.of(question));

        Question result = questionService.getQuestion(101L);

        assertEquals(question, result);
        verify(questionRepository, times(1)).findById(101L);
    }

    @Test
    void testGetQuestionsOfQuiz() {
        Set<Question> questions = new HashSet<>(Collections.singletonList(question));
        when(questionRepository.findByQuiz(quiz)).thenReturn(questions);

        Set<Question> result = questionService.getQuestionsOfQuiz(quiz);

        assertEquals(1, result.size());
        assertTrue(result.contains(question));
        verify(questionRepository, times(1)).findByQuiz(quiz);
    }

    @Test
    void testDeleteQuestion() {
        doNothing().when(questionRepository).delete(any(Question.class));

        questionService.deleteQuestion(101L);

        verify(questionRepository, times(1)).delete(any(Question.class));
    }

    @Test
    void testGetQuestionUsingGetOne() {
        when(questionRepository.getOne(101L)).thenReturn(question);

        Question result = questionService.get(101L);

        assertEquals(question, result);
        verify(questionRepository, times(1)).getOne(101L);
    }
}

