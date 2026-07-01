package org.skypro.skyshop.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;
import java.util.Objects;
import java.util.UUID;


public abstract class Product implements Searchable{
    private String name;
    private final UUID id;

    public Product(String name) {
        if (!name.isBlank()){
            this.name = name;
            this.id = UUID.randomUUID();
        } else {
            throw new IllegalArgumentException("Недопустимое название продукта");
        }
    }

    public String getName() {return name;}
    public abstract int getPrice();

    public void setName(String name) {this.name = name;}

    public abstract boolean isSpecial();

    @Override
    public String getSearchableName() {return name;}
    @Override
    @JsonIgnore
    public String getSearchableType() {return "PRODUCT";};
    @Override
    @JsonIgnore
    public String getSearchableTerm() {return name;};
    @Override
    public UUID getId() {return id;}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}