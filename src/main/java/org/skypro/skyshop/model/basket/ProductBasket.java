package org.skypro.skyshop.model.basket;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class ProductBasket {
    private final Map<UUID, Integer> basketMap = new HashMap<>();

    public void addProduct(UUID id) {
        basketMap.merge(id, 1, Integer::sum);
    }

    public Map<UUID, Integer> getBasketMap() {
        return Collections.unmodifiableMap(basketMap);
    }


}
