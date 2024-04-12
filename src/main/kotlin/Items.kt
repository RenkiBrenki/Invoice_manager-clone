import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import kotlin.collections.LinkedHashMap

class Items : LinkedHashMap<String, Item>(), Searchable {
    private var id = UUID.randomUUID()
    override fun put(key: String, value: Item): Item? {
        val item = get(key)
        if (item != null) {
            item.quantity += 1
            return item
        }
        return super.put(key, value)
    }

    override fun remove(key: String, value: Item): Boolean {
        return super<java.util.LinkedHashMap>.remove(key, value)
    }

    override fun remove(key: String): Item? {
        val item = get(key)
        if (item != null) {
            if(item.quantity > 0) {
                item.quantity --
            } else {
                throw IllegalStateException("Failed to remove item, item quantity 0")
            }
            return item
        }
        return super.remove(key)
    }
    override fun toString(): String {
        val itemsString = StringBuilder()
        for ((_, item) in this) {
            itemsString.append(item.toString() + "\n")
        }
        return "$itemsString"
    }
    fun getTotalPrice() : BigDecimal {
        var total = BigDecimal(0.0)
        for((_, item) in this) {
            if (item.quantity > 0) {
                total += item.price.multiply(BigDecimal(item.quantity))
            }
        }
        return total.setScale(2, RoundingMode.UP)
    }
}