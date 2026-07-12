package org.skypro.skyshop.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.exception.NoSuchProductException;

import org.springframework.stereotype.Service;

@Service
public class BasketService {
    private final ProductBasket productBasket;
    private final StorageService storageService;

    public BasketService(ProductBasket productBasket, StorageService storageService) {
        this.productBasket = productBasket;
        this.storageService = storageService;
    }

    public void addProduct(UUID id) {
        storageService.getProductById(id)
                .orElseThrow(() -> new NoSuchProductException("Продукт с ID " + id + " не найден в хранилище!"));
        productBasket.addProduct(id);
    }

    public UserBasket getUserBasket() {
        Map<UUID, Integer> tempBasket = productBasket.getBasketMap();
        List<BasketItem> basketItems = tempBasket.entrySet().stream()
                .map(entry -> {
                    UUID id = entry.getKey();
                    int quantity = entry.getValue();
                    Product product = storageService.getProductById(id).get();
                    return new BasketItem(product, quantity);
                })
                .toList();
        return new UserBasket(basketItems);
    }
}
