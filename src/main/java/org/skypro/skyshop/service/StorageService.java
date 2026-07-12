package org.skypro.skyshop.service;

import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Optional;

import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.product.DiscountedProduct;

@Service
public class StorageService {
    private final Map<UUID, Product> productMap = new ConcurrentHashMap<UUID, Product>();
    private final Map<UUID, Article> articleMap = new ConcurrentHashMap<UUID, Article>();

    public StorageService() {}

    public void add(Article article){
        articleMap.put(article.getId(), article);
    }

    public void add(Product product){
        productMap.put(product.getId(), product);
    }

    public Map<UUID, Product> getProductMap() {
        return productMap;
    }

    public Map<UUID, Article> getArticleMap() {
        return articleMap;
    }

    public Collection<Searchable> getAllSearchable() {
        return Stream.concat(
                productMap.values().stream(),
                articleMap.values().stream()
        ).collect(Collectors.toList());
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(productMap.get(id));
    }

    @PostConstruct
    private void tempAdd() {
        SimpleProduct example1 = new SimpleProduct("Ex1", 1);
        SimpleProduct example2 = new SimpleProduct("Ex2", 2);
        DiscountedProduct example3 = new DiscountedProduct("Ex3", 100, 30);
        FixPriceProduct example4 = new FixPriceProduct("Ex4");
        SimpleProduct example5 = new SimpleProduct("Ex5", 5);
        SimpleProduct example6 = new SimpleProduct("Ex6", 6);

        this.add(example1);
        this.add(example2);
        this.add(example3);
        this.add(example4);
        this.add(example5);
        this.add(example6);

        Article article1 = new Article("Ar1", "Ar1text");
        Article article2 = new Article("Ar2", "Ar2text");

        this.add(article1);
        this.add(article2);
    }


}
