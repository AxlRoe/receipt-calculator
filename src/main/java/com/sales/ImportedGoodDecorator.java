package com.sales;

/**
 * This class defines how to calculate taxes for imported goods
 */
public class ImportedGoodDecorator implements Good {

    private Good good;
    private SettingsBean settingsBean;

    public ImportedGoodDecorator(Good good, SettingsBean settingsBean) {
        if (settingsBean == null) {
            this.settingsBean = new SettingsBean();
        } else {
            this.settingsBean = settingsBean;
        }
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
        double importedTaxRate = settingsBean.getImportedTax();
        return TaxCalculator.calculate(good.getPrice()*good.getQty(), importedTaxRate) + good.getTax();
    }

    @Override
    public double getPrice() {
        return good.getPrice();
    }

    @Override
    public double getTotal() {
        return good.getPrice()*good.getQty() + getTax();
    }
}
