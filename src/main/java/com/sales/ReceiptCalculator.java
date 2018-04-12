package com.sales;

import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class ReceiptCalculator {

    private static final Logger logger = Logger.getLogger(ReceiptCalculator.class);
    private List<Good> basket = new ArrayList<Good>();

    /**
     * add an item to receipt
     * @param item to add
     */
    public void addItem (String item) {
        if (item != null) {
            Good good = GoodFactory.getGood(item, Settings.getInstance().getSettingsBean());
            if (good != null) {
                basket.add(good);
            } else {
                logger.error("Impossible to add an item to a receipt ");
            }
        }
    }

    /**
     * calculate total amount of sales taxes
     * @return total amount of sales taxes
     */
    private double calculateSalesTaxes () {
        return basket.stream().mapToDouble(g -> g.getTax()).sum();
    }

    /**
     * calculate total of the receipt
     * @param taxes to consider
     * @return total of the receipt
     */
    private double calculateTotal(double taxes) {
        return basket.stream().mapToDouble(g -> g.getPrice()).sum() + taxes;
    }

    /**
     * print all items of the receipt, taxes and total
     * @return the receipt as a string
     */
    public String printReceipt () {
        // specify output format for prices and taxes
        DecimalFormat df = new DecimalFormat("#0.00");
        DecimalFormatSymbols  dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);

        final StringBuilder sb = new StringBuilder();
        double taxes = calculateSalesTaxes();
        double total = calculateTotal(taxes);

        basket.stream().forEach(e -> {
            sb.append(e.getQty())
                    .append(" ")
                    .append(e.getName() + ": ")
                    .append(df.format(e.getTotal()))
                    .append("\n");
        });

        sb.append("Sales Taxes: " + df.format(taxes)).append("\n");
        sb.append("Total: " + df.format(total));
        return sb.toString();
    }
}
