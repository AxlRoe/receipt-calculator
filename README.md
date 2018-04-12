Note:

- method testReceiptCalculator of ReceiptCalculatorIntegrationTest is used to 
  obtain the receipt using the test cases written in input.json file 

- the input that receive ReceiptCalculator must follow the pattern:
  1 chocolate at 10.34 or 23 perfume at 34.34
  Not allowed: box at 132 (missing qty) or 1 perfume 34.65 (missing at),or perfume at 23
  in these case -1 will be returne for quantity and NaN for price of good
  
- settings.json: here user can define which goods are exempt by taxes and which 
  are the tax rate applied to imported and all goods.
  For exempts good, user express a regular expression that good inserted in the receipt
  must match if it is an exempt good 
  