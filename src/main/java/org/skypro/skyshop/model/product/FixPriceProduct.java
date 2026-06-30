package org.skypro.skyshop.model.product;

import java.util.UUID;

public class FixPriceProduct extends Product{
    private static int price = 150;
    private final UUID id;

    public FixPriceProduct(String name) {
        super(name);
        this.id = UUID.randomUUID();;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public boolean isSpecial(){
        return true;
    }

    @Override
    public String toString() {
        return getName() + ": Фиксированная цена " + getPrice();
    }

    @Override
    public UUID getId() {return id;}
}
