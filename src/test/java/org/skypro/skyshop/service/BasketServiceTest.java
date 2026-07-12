package org.skypro.skyshop.service;

import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.exception.NoSuchProductException;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.service.BasketService;
import org.skypro.skyshop.service.StorageService;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {
    @Mock
    private ProductBasket productBasket;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private BasketService basketService;

    private UUID productId;
    private Product product;

    @BeforeEach
    void setUp(){
        productId = UUID.randomUUID();
        product = new SimpleProduct("Test", 123);
    }

    @Test
    void addProduct_ShouldThrowException_WhenProductDoesNotExistInStorage(){
        when(storageService.getProductById(productId)).thenReturn(Optional.empty());
        assertThrows(NoSuchProductException.class, () -> basketService.addProduct(productId));
        verify(productBasket, never()).addProduct(any());
    }

    @Test
    void addProduct_ShouldCallBasketAddProduct_WhenProductExistsInStorage(){
        when(storageService.getProductById(productId)).thenReturn(Optional.of(product));
        basketService.addProduct(productId);
        verify(productBasket, times(1)).addProduct(productId);
    }

    @Test
    void getUserBasket_ShouldReturnEmptyBasket_WhenProductBasketIsEmpty(){
        when(productBasket.getBasketMap()).thenReturn(Collections.emptyMap());
        UserBasket userBasket = basketService.getUserBasket();

        assertNotNull(userBasket);
        assertTrue(userBasket.getItems().isEmpty(), "Список товаров должен быть пуст");
        assertEquals(0, userBasket.getTotal(), "Итоговая стоимость должна быть 0");
    }

    @Test
    void getUserBasket_ShouldReturnCorrectBasket_WhenProductBasketHasItems() {
        int quantity = 3;
        when(productBasket.getBasketMap()).thenReturn(Map.of(productId, quantity));
        when(storageService.getProductById(productId)).thenReturn(Optional.of(product));

        UserBasket userBasket = basketService.getUserBasket();

        assertNotNull(userBasket);
        assertEquals(1, userBasket.getItems().size(), "В корзине должен быть 1 товар");

        BasketItem item = userBasket.getItems().get(0);
        assertEquals(product, item.getProduct(), "Продукт должен совпадать");
        assertEquals(quantity, item.getQuantity(), "Количество должно быть верным");

        assertEquals(369, userBasket.getTotal(), "Общая стоимость рассчитана неверно");
    }
}
