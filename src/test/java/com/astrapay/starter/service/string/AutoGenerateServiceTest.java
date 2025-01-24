package com.astrapay.starter.service.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Raymond Sugiarto
 * @since 2022-02-09
 */
@ExtendWith(SpringExtension.class)
class AutoGenerateServiceTest {

  private static final int CUSTOM_LENGTH = 10;

  @Test
  void testGetRandomAlphaNumeric() {
    CodeGeneratorService autoGenerateCodeService = new CodeGeneratorService();
    String randomAlphaNumeric = autoGenerateCodeService.getRandomAlphaNumeric("INV");
    Assertions.assertNotNull(randomAlphaNumeric);
    Assertions.assertEquals(CodeGeneratorService.MAX_LENGTH - 3, randomAlphaNumeric.length());
  }

  @Test
  void testGetRandomAlphaNumericWithLength() {
    CodeGeneratorService autoGenerateCodeService = new CodeGeneratorService();
    String randomAlphaNumeric = autoGenerateCodeService.getRandomAlphaNumeric("INV", CUSTOM_LENGTH);
    Assertions.assertNotNull(randomAlphaNumeric);
    Assertions.assertEquals(CUSTOM_LENGTH - 3, randomAlphaNumeric.length());
  }

  @Test
  void testGenerateCode() {
    CodeGeneratorService autoGenerateCodeService = new CodeGeneratorService();
    String code = autoGenerateCodeService.generateCode("INV/QRI");
    Assertions.assertNotNull(code);
    Assertions.assertEquals(CodeGeneratorService.MAX_LENGTH, code.length());
  }

  @Test
  void testGenerateCodeWithLength() {
    CodeGeneratorService autoGenerateCodeService = new CodeGeneratorService();
    String code = autoGenerateCodeService.generateCode("INV/QRI", CUSTOM_LENGTH);
    Assertions.assertNotNull(code);
    Assertions.assertEquals(CUSTOM_LENGTH, code.length());
  }

  @Test
  void testGenerateNumericCode() {
    CodeGeneratorService autoGenerateCodeService = new CodeGeneratorService();
    String code = autoGenerateCodeService.generateNumericCode("INV/QRI");
    Assertions.assertNotNull(code);
    Assertions.assertEquals(CodeGeneratorService.MAX_LENGTH, code.length());
  }

  @Test
  void testGenerateNumericCodeWithLength() {
    CodeGeneratorService autoGenerateCodeService = new CodeGeneratorService();
    String code = autoGenerateCodeService.generateNumericCode("INV/QRI", CUSTOM_LENGTH);
    Assertions.assertNotNull(code);
    Assertions.assertEquals(CUSTOM_LENGTH, code.length());
  }

  @Test
  void testGenerateInvoiceNumber() {
    CodeGeneratorService autoGenerateCodeService = new CodeGeneratorService();
    String code = autoGenerateCodeService.generateInvoiceNumber("QRI/");
    Assertions.assertNotNull(code);
    Assertions.assertEquals(CodeGeneratorService.MAX_LENGTH, code.length());
    Assertions.assertTrue(code.startsWith(CodeGeneratorService.Prefix.INVOICE.getPrefix()));
  }

  @Test
  void testGetRandomNumeric() {
    CodeGeneratorService autoGenerateCodeService = new CodeGeneratorService();
    String randomNumeric = autoGenerateCodeService.getRandomNumeric("INV");
    Assertions.assertNotNull(randomNumeric);
    Assertions.assertEquals(CodeGeneratorService.MAX_LENGTH - 3, randomNumeric.length());
  }

  @Test
  void testGetRandomNumericWithLength() {
    CodeGeneratorService autoGenerateCodeService = new CodeGeneratorService();
    String randomNumeric = autoGenerateCodeService.getRandomNumeric("INV", CUSTOM_LENGTH);
    Assertions.assertNotNull(randomNumeric);
    Assertions.assertEquals(CUSTOM_LENGTH - 3, randomNumeric.length());
  }
}
