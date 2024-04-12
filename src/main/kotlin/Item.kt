import util.BarcodeUtil
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

open class Item (var name: String, var price: BigDecimal, var barcode: String, var taxRate: BigDecimal = BigDecimal(0.22), var quantity: Int = 1): Searchable {
    var id = UUID.randomUUID().toString().replace("-","")

    init {
        if (!BarcodeUtil.isBarcodeValid(barcode)) {
            throw IllegalArgumentException()
        }
    }

    override fun toString(): String {
        return "$name\t\t\t$quantity\t\t\t${price.setScale(2, RoundingMode.UP)}"
    }
}