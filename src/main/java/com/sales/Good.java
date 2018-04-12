package com.sales;

/**
 * defines methods used to retrieve needed information
 * about items in the receipt
 *
 */
public interface Good {

    /**
     * get the name of product in the receipt
     * @return name of product
     */
    String getName();

    /**
     * get quantity of the product in the receipt
     * @return quantity of product
     */
    int getQty();

    /**
     *
     * @return
     */
    double getTax();

    double getPrice();

    double getTotal();

}
