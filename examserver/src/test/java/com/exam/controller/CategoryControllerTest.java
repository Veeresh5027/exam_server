package com.exam.controller;

import com.exam.Service.CategoryService;
import com.exam.model.exam.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category();
        category.setCid(1L);
        category.setTitle("Java");
        category.setDescription("Java Category");
    }

    @Test
    void testAddCategory() {
        when(categoryService.addCategory(any(Category.class))).thenReturn(category);

        ResponseEntity<Category> response = categoryController.addCategory(category);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(category, response.getBody());
        verify(categoryService, times(1)).addCategory(category);
    }

    @Test
    void testGetCategory() {
        when(categoryService.getCategory(1L)).thenReturn(category);

        Category result = categoryController.getCategory(1L);

        assertEquals(category, result);
        verify(categoryService, times(1)).getCategory(1L);
    }

    @Test
    void testGetCategories() {
        Set<Category> categories = Set.of(category); // Creates an immutable set
        when(categoryService.getCategories()).thenReturn(categories);

        ResponseEntity<?> response = categoryController.getCategories();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(categories, response.getBody());
        verify(categoryService, times(1)).getCategories();
    }


    @Test
    void testUpdateCategory() {
        when(categoryService.updateCategory(any(Category.class))).thenReturn(category);

        Category updated = categoryController.updateCategory(category);

        assertEquals(category, updated);
        verify(categoryService, times(1)).updateCategory(category);
    }

    @Test
    void testDeleteCategory() {
        doNothing().when(categoryService).deleteCategory(1L);

        categoryController.deleteCategory(1L);

        verify(categoryService, times(1)).deleteCategory(1L);
    }
}
