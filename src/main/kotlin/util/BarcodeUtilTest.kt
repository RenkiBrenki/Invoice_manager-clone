package util

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.math.BigDecimal

class BarcodeUtilTest {
    private val testBarcodeUtil = BarcodeUtil

    @Test
    fun testValidBarcode() {
        val barcode = "2114356002002"
        assertTrue(testBarcodeUtil.isBarcodeValid(barcode))
    }
    @Test
    fun testInvalidBarcode() {
        val invalidBarcode1 = "2124356f0200r"
        val invalidBarcode2 = "2f23424002003"
        val invalidBarcode3 = "211435600205"
        assertFalse(testBarcodeUtil.isBarcodeValid(invalidBarcode1))
        assertFalse(testBarcodeUtil.isBarcodeValid(invalidBarcode2))
        assertFalse(testBarcodeUtil.isBarcodeValid(invalidBarcode3))
    }
    @Test
    fun testGenerateBarcode() {
        val expectedBarcode = "2114356002002"
        assertEquals(expectedBarcode, testBarcodeUtil.generateBarcode(testBarcodeUtil.createInternalItem(expectedBarcode)))
    }
    @Test
    fun testCreateInternalItem() {
        //211 4356 00200 2 barcode
        val barcode = "2114356002002"
        val expectedInternalItem = InternalItem(
            Department.Fruit,
            "4356",
            200,
            "Interna",
            BigDecimal(3.44),
            barcode)
        assertTrue(expectedInternalItem == testBarcodeUtil.createInternalItem(barcode))
    }
    @Test
    fun testGenerateCheckDigit() {
        val expected = '2'
        assertEquals(expected, testBarcodeUtil.generateCheckDigit("211435600200"))
    }
}