package com.sales;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GoodFactory {

    enum GoodType {EXEMPT, IMPORTED};
    private static final Logger logger = Logger.getLogger(GoodFactory.class);
    private static final String AT_TOKEN = "at";
    private static final String UNDEFINED_GOOD = "<undefined>";

    /**
     * split input item in token
     * @param item from receipt
     * @return list of token composing the item (always not null)
     */
    private static List<String> getTokenizedItem(String item) {
        String[] tokenizedItemArray = item.split(" ");
        if (tokenizedItemArray == null || tokenizedItemArray.length == 0) {
            logger.error("Invalid item ");
            return new ArrayList<>();
        }

        return new ArrayList<>(Arrays.asList(tokenizedItemArray));
    }

    /**
     * extract item quantity from list of token
     *
     * @param tokens list of token composing the item
     * @return quantity of the item
     */
    private static int extractQuantity (List<String> tokens) {
        try {
            int quantity = Integer.parseInt(tokens.get(0));
            tokens.remove(0);
            return quantity;
        } catch (NumberFormatException ne) {
            logger.error("Invalid quantity", ne);
        }

        return -1;
    }

    /**
     * extract item price from list of token
     *
     * @param tokens list of token composing the item
     * @return price of the item
     */
    private static double extractPrice (List<String> tokens) {
        try {
            double price = Double.parseDouble(tokens.get(tokens.size() - 1));
            tokens.remove(tokens.size() - 1);
            if (AT_TOKEN.equalsIgnoreCase(tokens.get(tokens.size() - 1))) {
                tokens.remove(tokens.size() - 1);
                return price;
            } else {
                logger.error("Impossible to retrieve price");
            }
        } catch (NumberFormatException ne) {
            logger.error("Invalid price", ne);
        }

        return Double.NaN;
    }

    /**
     * retrieve name of the item price
     *
     * @param tokens list of token composing the item
     * @return name of the item
     */
    private static String parseName (List<String> tokens) {
        if (tokens.size() == 0) {
            logger.warn("Thre is an undefined item in the receipt");
            return UNDEFINED_GOOD;
        }

        return tokens.stream().collect(Collectors.joining(" "));
    }

    /**
     * retrieve which types an item have (imported, exempt, etc..)
     *
     * @param tokens list of token composing the item
     * @return list of types
     */
    private static List<GoodType> parseTypes (List<String> tokens, SettingsBean settingsBean) {

        List<String> exemptGoods = settingsBean.getExempt();
        final List<GoodType> types = new ArrayList<>();
        tokens.stream().forEach(t -> {
            if (GoodType.IMPORTED.name().equalsIgnoreCase(t)) {
                types.add(GoodType.IMPORTED);
            } else {
                Optional<String> type = exemptGoods.stream().filter(e -> t.matches(e)).findFirst();
                if (type.isPresent()) {
                    types.add(GoodType.EXEMPT);
                }
            }
        });

        return types;
    }

    /**
     * retrive good related to a given item
     *
     * @param item from receipt
     * @param settingsBean
     * @return which good item correspond to
     */
    public static Good getGood(String item, SettingsBean settingsBean) {

        if (item == null || settingsBean == null) {
            logger.error("Invalid item or settings");
            return null;
        }

        Good basicGood, goodDecorated;
        List<String> tokens = getTokenizedItem(item);
        int quantity = extractQuantity(tokens);
        double price = extractPrice(tokens);
        String name = parseName(tokens);
        List<GoodType> types = parseTypes(tokens, settingsBean);

        // According to the types associated to the item, choose the
        // appropriate decorator
        basicGood = new BasicGood(name, price, quantity);
        if (types.contains(GoodType.EXEMPT)) {
            goodDecorated = new ExemptGoodDecorator(basicGood);
        } else {
            goodDecorated = new GoodDecorator(basicGood, settingsBean);
        }

        if (types.contains(GoodType.IMPORTED)) {
            return new ImportedGoodDecorator(goodDecorated, settingsBean);
        } else {
            return goodDecorated;
        }
    }

}
