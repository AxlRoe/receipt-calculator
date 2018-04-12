package com.sales;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * bean used to map settings json file
 */
public class SettingsBean {

    @JsonProperty("exempt")
    public List<String> exempt = null;
    @JsonProperty("standardTax")
    public double standardTax;
    @JsonProperty("importedTax")
    public double importedTax;

    public SettingsBean() {
        exempt = new ArrayList<>();
        standardTax = Double.NaN;
        importedTax = Double.NaN;
    }

    public List<String> getExempt() {
        return exempt;
    }

    public double getStandardTax() {
        return standardTax;
    }

    public double getImportedTax() {
        return importedTax;
    }

}
