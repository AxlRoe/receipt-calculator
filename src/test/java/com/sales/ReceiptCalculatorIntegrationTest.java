package com.sales;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.BasicConfigurator;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * test class used to perform integration tests
 *
 * file with test case is a json composed in this way
 * {
 *     "items" : {
 *         "1" : "<item1>;<item2>..." (list of item composing the receipt separated by ;)
 *     }
 * }
 *
 * file with expected receipt is a json composed in this way
 * {
 *     "items" : {
 *         "1" : "<item1>;<item2>;Taxes;Total (all items of the receipt separated by ;)
 *     }
 * }
 *
 */
@RunWith(Parameterized.class)
public class ReceiptCalculatorIntegrationTest {

    private static final String IN_FILE = "input.json";
    private static final String OUT_FILE = "output.json";

    private String testCase, expectedResult;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void before() {
        BasicConfigurator.configure();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() throws Exception {

        List<Object[]> tests = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // read test cases and related expected result from json
        JSONObject inJson = new JSONObject(new JSONTokener(ReceiptCalculatorIntegrationTest.class.getResourceAsStream(IN_FILE)));
        JSONObject outJson = new JSONObject(new JSONTokener(ReceiptCalculatorIntegrationTest.class.getResourceAsStream(OUT_FILE)));
        JsonBean inJsonBean = mapper.readValue (inJson.toString(), JsonBean.class);
        JsonBean outJsonBean = mapper.readValue (outJson.toString(), JsonBean.class);

        // fill list of testcases
        for (String key : inJsonBean.getItemMap().keySet()) {
            Object[] item = new Object[2];
            item[0] = inJsonBean.getItemMap().get(key);
            item[1] = outJsonBean.getItemMap().get(key);
            tests.add(item);
        }

        return tests;
    }

    public ReceiptCalculatorIntegrationTest (String testCase, String expectedResult) {
        this.testCase = testCase;
        this.expectedResult = expectedResult;
    }

    @Test
    public void testReceiptCalculator () {

        // build the expected receipt
        final StringBuilder expectedReceipt = new StringBuilder();
        List<String> results = Arrays.asList(expectedResult.split(";"));
        results.stream().limit(results.size() - 1).forEach(result -> {
            expectedReceipt.append(result).append("\n");
        });
        expectedReceipt.append(results.get(results.size() - 1));

        // get list of item used to compose the receipt
        List<String> items = Arrays.asList(testCase.split(";"));

        // calculate the receipt
        final ReceiptCalculator rc = new ReceiptCalculator();
        items.stream().forEach(i -> rc.addItem(i));

        Assert.assertEquals("Wrong expected receipt ", expectedReceipt.toString(), rc.printReceipt());
    }


}
