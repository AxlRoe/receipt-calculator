package com.sales;

/**
 * This class defines how to calculate price for
 * good exempted by taxes
 */
public class ExemptGoodDecorator implements Good {

    private Good good;

    public ExemptGoodDecorator(Good good) {
        this.good = good;
    }

    public String getName() {
        return good.getName();
    }

    @Override
    public int getQty() {
        return good.getQty();
    }

    @Override
    public double getTax() {
        return good.getTax();
    }

    @Override
    public double getPrice() {
        return good.getPrice();
    }

    @Override
    public double getTotal() {
        return good.getTotal();
    }
}
