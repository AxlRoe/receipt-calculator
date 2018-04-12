package com.sales;

/**
 * Utility class used to calculate taxes implementing rounding rule
 */
public class TaxCalculator {

    private static final double STEP = 0.05;

    /**
     * calculate amount of taxes
     * @param price calculate taxes from
     * @param taxRate applied to price
     * @return amount of taxes
     */
    public static double calculate (double price, double taxRate) {

        if (Double.isNaN(taxRate) || Double.isNaN(price)) {
            return Double.NaN;
        }

        double tax = price*taxRate;
        double bound = Math.round(tax * 20.0) / 20.0;
        if (tax > bound) {
            // bound is the lower nearest 0.05
            return bound + STEP;
        }
        // bound is already the higher nearest up 0.05
        return bound;
    }

}
