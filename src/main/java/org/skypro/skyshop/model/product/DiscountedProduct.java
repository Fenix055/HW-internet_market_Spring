package org.skypro.skyshop.model.product;

import java.util.UUID;

public class DiscountedProduct extends Product {
    int price;
    int discount;
    private final UUID id;

    public DiscountedProduct(String name, int price, int discount) {
        super(name);
        if (price>0 && discount>=0 && discount<=100) {
            this.price = price;
            this.discount = discount;
            this.id = UUID.randomUUID();
        }else {
            throw new IllegalArgumentException("Неверное значение цены или скидки.");
        }
    }
    @Override
    public int getPrice() {
        return price-price/100*discount;
    }

    public int getDiscount() {
        return discount;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public boolean isSpecial(){
        return true;
    }

    @Override
    public String toString() {
        return getName() + ": " + getPrice() + " (" + getDiscount()+"%)";
    }

    @Override
    public UUID getId() {return id;}
}
