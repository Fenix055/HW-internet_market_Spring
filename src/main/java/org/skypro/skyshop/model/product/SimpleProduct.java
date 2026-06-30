package org.skypro.skyshop.model.product;

import java.util.UUID;

public class SimpleProduct extends Product {
    private int price;
    private final UUID id;

    public SimpleProduct(String name, int price) {
        super(name);
        if (price>0) {
            this.price = price;
            this.id = UUID.randomUUID();
        } else {
            throw new IllegalArgumentException("Цена не может быть меньше 1.");
        }
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public boolean isSpecial(){
        return false;
    }

    @Override
    public String toString() {
        return getName() + ": " + getPrice();
    }

    @Override
    public UUID getId() {return id;}
}
