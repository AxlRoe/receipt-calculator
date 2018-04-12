package com.sales;

/**
 * This class defines how to calculate taxes for all goods
 * not exempted by taxes
 */
public class GoodDecorator implements Good {

    private Good good;
    private SettingsBean settingsBean;

    public GoodDecorator(Good good, SettingsBean settingsBean) {
        if (settingsBean == null) {
            this.settingsBean = new SettingsBean();
        } else {
            this.settingsBean = settingsBean;
        }
        this.good = good;
    }

    @Override
    public String getName() {
        return good.getName();
    }

    @Override
    public int getQty() {
        return good.getQty();
    }

    @Override
    public double getTax() {
        double standardTaxRate = settingsBean.getStandardTax();
        return TaxCalculatorUtils.calculate(good.getPrice()*good.getQty(), standardTaxRate);
    }

    @Override
    public double getPrice() {
        return good.getPrice();
    }

    @Override
    public double getTotal() {
        return good.getTotal()+getTax();
    }

}
