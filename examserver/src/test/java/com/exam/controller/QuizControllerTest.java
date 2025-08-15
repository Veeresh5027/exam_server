package com.exam.controller;

import com.exam.Service.QuizService;
import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuizControllerTest {

    @Mock
    private QuizService quizService;

    @InjectMocks
    private QuizController quizController;

    private Quiz quiz;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        quiz = new Quiz();
        quiz.setQid(1L);
        quiz.setTitle("Spring Boot Basics");
        quiz.setDescription("Intro to Spring Boot");
        quiz.setActive(true);
    }

    @Test
    void testAddQuiz() {
        when(quizService.addQuiz(any(Quiz.class))).thenReturn(quiz);

        ResponseEntity<Quiz> response = quizController.addQuiz(quiz);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(quiz, response.getBody());
        verify(quizService, times(1)).addQuiz(quiz);
    }

    @Test
    void testUpdateQuiz() {
        when(quizService.updateQuiz(any(Quiz.class))).thenReturn(quiz);

        ResponseEntity<Quiz> response = quizController.updateQuiz(quiz);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(quiz, response.getBody());
        verify(quizService, times(1)).updateQuiz(quiz);
    }

    @Test
    void testGetQuizzes() {
        Set<Quiz> quizzes = Set.of(quiz);
        when(quizService.getQuizzes()).thenReturn(quizzes);


        ResponseEntity<?> response = quizController.getQuizzes();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(quizzes, response.getBody());
        verify(quizService, times(1)).getQuizzes();
    }

    @Test
    void testGetQuiz() {
        when(quizService.getQuiz(1L)).thenReturn(quiz);

        ResponseEntity<Quiz> response = quizController.getQuiz(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(quiz, response.getBody());
        verify(quizService, times(1)).getQuiz(1L);
    }

    @Test
    void testDeleteQuiz() {
        doNothing().when(quizService).deleteQuiz(1L);

        quizController.deleteQuiz(1L);

        verify(quizService, times(1)).deleteQuiz(1L);
    }

    @Test
    void testGetQuizzesOfCategory() {
        List<Quiz> quizzes = List.of(quiz);
        when(quizService.getQuizzesOfCategory(1L)).thenReturn(quizzes);

        List<Quiz> result = quizController.getQuizzesOfCategory(1L);

        assertEquals(quizzes, result);
        verify(quizService, times(1)).getQuizzesOfCategory(1L);
    }

    @Test
    void testGetActiveQuizzes() {
        List<Quiz> quizzes = List.of(quiz);
        when(quizService.getActiveQuizzes()).thenReturn(quizzes);

        List<Quiz> result = quizController.getActiveQuizzes();

        assertEquals(quizzes, result);
        verify(quizService, times(1)).getActiveQuizzes();
    }

    @Test
    void testGetActiveQuizzesOfCategory() {
        List<Quiz> quizzes = List.of(quiz);
        Category category = new Category();
        category.setCid(1L);
        when(quizService.getActiveQuizzesOfCategory(any(Category.class))).thenReturn(quizzes);

        List<Quiz> result = quizController.getActiveQuizzesOfCategory(1L);

        assertEquals(quizzes, result);
        verify(quizService, times(1)).getActiveQuizzesOfCategory(any(Category.class));
    }

    // -------- Negative Scenarios --------

    @Test
    void testGetQuiz_NotFound() {
        when(quizService.getQuiz(999L)).thenReturn(null);

        ResponseEntity<Quiz> response = quizController.getQuiz(999L);

        assertNull(response.getBody());
        verify(quizService, times(1)).getQuiz(999L);
    }

    @Test
    void testGetActiveQuizzes_Empty() {
        when(quizService.getActiveQuizzes()).thenReturn(List.of());

        List<Quiz> result = quizController.getActiveQuizzes();

        assertTrue(result.isEmpty());
        verify(quizService, times(1)).getActiveQuizzes();
    }
}
