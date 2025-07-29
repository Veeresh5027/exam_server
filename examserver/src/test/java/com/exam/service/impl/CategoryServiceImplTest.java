package com.exam.service.impl;

import com.exam.Service.impl.CategoryServiceImpl;
import com.exam.model.exam.Category;
import com.exam.repo.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category();
        category.setCid(1L);
        category.setTitle("Programming");
        category.setDescription("Technical programming content");
    }

    @Test
    void testAddCategory() {
        when(categoryRepository.save(category)).thenReturn(category);

        Category result = categoryService.addCategory(category);

        assertEquals(category, result);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testUpdateCategory() {
        when(categoryRepository.save(category)).thenReturn(category);

        Category result = categoryService.updateCategory(category);

        assertEquals(category, result);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testGetCategories() {
        List<Category> list = Arrays.asList(category);
        when(categoryRepository.findAll()).thenReturn(list);

        Set<Category> result = categoryService.getCategories();

        assertEquals(1, result.size());
        assertTrue(result.contains(category));
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testGetCategoryById() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Category result = categoryService.getCategory(1L);

        assertEquals(category, result);
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteCategory() {
        doNothing().when(categoryRepository).delete(any(Category.class));

        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).delete(any(Category.class));
    }
}

