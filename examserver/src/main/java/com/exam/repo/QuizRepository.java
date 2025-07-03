package com.exam.repo;

import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    // get all quizzes of category
    public List<Quiz> findByCategory(Category category);

    public List<Quiz> findByActive(boolean b);

    public List<Quiz> findByCategoryAndActive(Category category, boolean b);
}
