package org.skypro.skyshop.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    private Searchable product;
    private Searchable article;

    @BeforeEach
    void setUp() {
        product = new SimpleProduct("Test", 123);
        article = new Article("Test article name", "Test article text");
    }

    @Test
    void search_ShouldReturnEmptyCollection_WhenStorageIsEmpty() {
        when(storageService.getAllSearchable()).thenReturn(Collections.emptyList());
        Collection<SearchResult> result = searchService.search("Test");

        assertNotNull(result, "Результат не должен быть null");
        assertTrue(result.isEmpty(), "Коллекция должна быть пустой");

        verify(storageService, times(1)).getAllSearchable();
    }

    @Test
    void search_ShouldReturnEmptyCollection_WhenNoMatchingObjectsFound() {
        when(storageService.getAllSearchable()).thenReturn(List.of(product, article));
        Collection<SearchResult> result = searchService.search("False");

        assertNotNull(result);
        assertTrue(result.isEmpty(), "Коллекция должна быть пустой");

        verify(storageService, times(1)).getAllSearchable();
    }

    @Test
    void search_ShouldReturnMatchingResults_WhenObjectsFound() {
        when(storageService.getAllSearchable()).thenReturn(List.of(product, article));
        Collection<SearchResult> result = searchService.search("Tes");

        assertNotNull(result);
        assertEquals(2, result.size(), "Должно быть найдено 2 объекта");

        List<SearchResult> resultList = result.stream().toList();

        assertEquals(product.getId().toString(), resultList.get(0).getId());
        assertEquals("Test", resultList.get(0).getName());
        assertEquals("PRODUCT", resultList.get(0).getContentType());

        assertEquals(article.getId().toString(), resultList.get(1).getId());
        assertEquals("Test article name", resultList.get(1).getName());
        assertEquals("ARTICLE", resultList.get(1).getContentType());

        verify(storageService, times(1)).getAllSearchable();
    }
}
