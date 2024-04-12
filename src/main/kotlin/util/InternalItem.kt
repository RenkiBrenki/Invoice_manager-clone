package util

import Item
import java.math.BigDecimal
import kotlin.random.Random
import kotlin.reflect.full.declaredMemberProperties

class InternalItem(
    var department: Department,
    var internalId: String,
    var weight: Int,
    name: String,
    price: BigDecimal,
    barcode: String,
    taxRate: BigDecimal = BigDecimal(9.5),
    quantity: Int = 1
) :
    Item(name, price, barcode, taxRate, quantity) {

    override fun toString(): String {
        return super.toString() + "\t${department.name}\t${weight}g" //\t$internalId $name $barcode $taxRate"
    }
    private fun generateId(): String {
        val random = Random(System.currentTimeMillis())
        val randomNumber = random.nextInt(1000, 10000)
        return randomNumber.toString()
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is InternalItem) return false

        val properties = this::class.declaredMemberProperties

        for (property in properties) {
            val thisValue = property.getter.call(this)
            val otherValue = property.getter.call(other)

            if (thisValue != otherValue) return false
        }

        return true
    }
}