package util

import java.math.BigDecimal
import kotlin.reflect.full.declaredMemberProperties

object BarcodeUtil {
    fun isBarcodeValid(barcode: String): Boolean {
        return barcode.length == 13 && barcode.all { it.isDigit() }
    }

    fun generateBarcode(item: InternalItem): String {
        val barcode = item.department.id + item.internalId + item.weight.toString().padStart(5,'0')
        return barcode + generateCheckDigit(barcode)
    }

    fun createInternalItem(barcode: String): InternalItem {
        if (isBarcodeValid(barcode)) {
            try {
                val departmentId: String = barcode.substring(0, 3)
                val department: Department? = Department.entries.find { it.id == departmentId }

                val id = barcode.substring(3, 7)
                val weight: Int? = barcode.substring(7, 12).toIntOrNull()

                if (department != null && weight != null) {
                    return InternalItem(department, id, weight, "Interna", BigDecimal(3.43), barcode)
                }
            } catch (ex: Exception)  {
                print("Failed to generate internal item with error: ${ex.message}")
            }
        }
        throw IllegalArgumentException()
    }

    fun generateCheckDigit(barcode: String): Char {
        if (barcode.length != 12) throw IllegalArgumentException()

        var sum = 0
        for (i in barcode.indices) {
            val multiplier = if ((i + 1) % 2 == 0) 3 else 1
            sum += Character.getNumericValue(barcode[i]) * multiplier
        }

        val remainder = sum % 10
        val number = if (remainder != 0) {
            sum + (10 - remainder)
        } else {
            sum
        }

        return (number - sum).digitToChar()
    }
}