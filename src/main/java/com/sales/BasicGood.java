package com.sales;

/**
 * Basic implementation of Good interface
 */
public class BasicGood implements Good {

    private double price;
    private String name;
    private int quantity;

    public BasicGood(String name, double price, int quantity) {
        this.price = price;
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getQty() {
        return quantity;
    }

    @Override
    public double getTax() {
        return 0;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getTotal() {
        return price*quantity;
    }
}
