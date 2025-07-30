package com.exam.service.impl;

import com.exam.Service.impl.QuizServiceImpl;
import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import com.exam.repo.QuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceImplTest {

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private QuizServiceImpl quizService;

    private Quiz quiz;
    private Category category;

    @BeforeEach
    void setup() {
        category = new Category();
        category.setCid(1L);
        category.setTitle("Java");

        quiz = new Quiz();
        quiz.setQid(101L);
        quiz.setTitle("Core Java Quiz");
        quiz.setCategory(category);
        quiz.setActive(true);
    }

    @Test
    void testAddQuiz() {
        when(quizRepository.save(quiz)).thenReturn(quiz);

        Quiz savedQuiz = quizService.addQuiz(quiz);

        assertNotNull(savedQuiz);
        assertEquals("Core Java Quiz", savedQuiz.getTitle());
        verify(quizRepository).save(quiz);
    }

    @Test
    void testUpdateQuiz() {
        when(quizRepository.save(quiz)).thenReturn(quiz);

        Quiz updated = quizService.updateQuiz(quiz);

        assertEquals(quiz.getQid(), updated.getQid());
        verify(quizRepository).save(quiz);
    }

    @Test
    void testGetQuizzes() {
        List<Quiz> quizzes = Arrays.asList(quiz);
        when(quizRepository.findAll()).thenReturn(quizzes);

        Set<Quiz> result = quizService.getQuizzes();

        assertEquals(1, result.size());
        assertTrue(result.contains(quiz));
        verify(quizRepository).findAll();
    }

    @Test
    void testGetQuiz() {
        when(quizRepository.findById(101L)).thenReturn(Optional.of(quiz));

        Quiz found = quizService.getQuiz(101L);

        assertNotNull(found);
        assertEquals(quiz.getQid(), found.getQid());
        verify(quizRepository).findById(101L);
    }

    @Test
    void testDeleteQuiz() {
        doNothing().when(quizRepository).delete(any(Quiz.class));

        quizService.deleteQuiz(101L);

        ArgumentCaptor<Quiz> quizCaptor = ArgumentCaptor.forClass(Quiz.class);
        verify(quizRepository).delete(quizCaptor.capture());
        assertEquals(101L, quizCaptor.getValue().getQid());
    }

    @Test
    void testGetQuizzesOfCategory() {
        when(quizRepository.findByCategory(any(Category.class))).thenReturn(List.of(quiz));

        List<Quiz> result = quizService.getQuizzesOfCategory(1L);

        assertEquals(1, result.size());
        assertEquals(category.getCid(), result.get(0).getCategory().getCid());
        verify(quizRepository).findByCategory(any(Category.class));
    }

    @Test
    void testGetActiveQuizzes() {
        when(quizRepository.findByActive(true)).thenReturn(List.of(quiz));

        List<Quiz> result = quizService.getActiveQuizzes();

        assertEquals(1, result.size());
        assertTrue(result.get(0).isActive());
        verify(quizRepository).findByActive(true);
    }

    @Test
    void testGetActiveQuizzesOfCategory() {
        when(quizRepository.findByCategoryAndActive(category, true)).thenReturn(List.of(quiz));

        List<Quiz> result = quizService.getActiveQuizzesOfCategory(category);

        assertEquals(1, result.size());
        assertTrue(result.get(0).isActive());
        assertEquals(category.getCid(), result.get(0).getCategory().getCid());
        verify(quizRepository).findByCategoryAndActive(category, true);
    }
}

