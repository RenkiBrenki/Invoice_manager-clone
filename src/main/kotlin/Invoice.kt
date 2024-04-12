import util.BarcodeUtil
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Instant
import java.util.*

class Invoice(
    var paymentMethod: PaymentMethod,
    var discount: BigDecimal = BigDecimal(0.0),
    var customer: Company? = null
) : Searchable {
    var id = UUID.randomUUID().toString().replace("-", "")
    var created = Date.from(Instant.now())
    var issuer = "Izdajatelj1"
    var cashier = "Rene Jausovec"

    var company = Company(
        "EatSmart", "Smetanova ulica 17, 2000 Maribor", "030674231", BigDecimal(0.22),
        "73832919", "SI563728327898732"
    )
        set(value) {
            field = value
            modified = Date.from(Instant.now())
        }
    var taxRate = BigDecimal(0.095)
        set(value) {
            field = value
            modified = Date.from(Instant.now())
        }
    val items = Items()

    private var modified = created

    init {
        manageItems()
    }

    private fun getTotalAmount(): BigDecimal {
        val tax = items.getTotalPrice() * taxRate
        return items.getTotalPrice() + tax
    }

    private fun calculateDiscount(): BigDecimal {
        return items.getTotalPrice() * discount
    }

    private fun manageItems() {
        val barcode = "2114356002002"
        val data = mutableListOf<Item>()

        try {
            data.add(Item("Malica1", BigDecimal(3.54), barcode))
            data.add(Item("Malica2", BigDecimal(4.34), barcode))
            data.add(Item("Malica3", BigDecimal(5.43), barcode))

            val internalItem = BarcodeUtil.createInternalItem(barcode)
            data.add(internalItem)

            print(BarcodeUtil.generateCheckDigit("211435600200") + "\n")
            print(BarcodeUtil.generateBarcode(internalItem) + "\n")

        } catch (ex: Exception) {
            print("Failed to initialize item with error: " + ex.message)
        }

        for (item in data) {
            this.items[item.id] = item
        }

        //add second item quantity
        this.items[data[1].id] = data[1]

        //remove
        try {
            //items.remove(data[0].id)
        } catch (ex: Exception) {
            print(ex.message + "\n")
        }
    }

    override fun toString(): String {
        val totalBeforeTax = items.getTotalPrice() - calculateDiscount()
        val totalAfterTax = this.getTotalAmount() - calculateDiscount()
        if (customer != null) {
            return "$company\n" +
                    "Racun stevilka: $id\n" +
                    "Datum placila: $created\n\n" +
                    "Kupec:\n ${customer.toString()}\n" +
                    "\t\t\t\tKolicina\tCena\tOddelek\tTeza\n" +
                    "$items\n" +
                    "Popust: ${discount.multiply(BigDecimal(100)).setScale(2, RoundingMode.DOWN)}%\n" +
                    "Za placilo brez DDV: ${totalBeforeTax.setScale(2, RoundingMode.UP)}\n" +
                    "Za placilo skupaj(${
                        taxRate.multiply(BigDecimal(100)).setScale(2, RoundingMode.DOWN)
                    }% DDV): ${totalAfterTax.setScale(2, RoundingMode.UP)}EUR\n\n" +
                    "Blagajna: ${company.cashRegister}\n" +
                    "Nacin placila: $paymentMethod \n" +
                    "Blagajnik: ${company.cashierFullName}\n\n" +
                    "\t\t\tHvala za obisk!"
        }
        return "$company\n" +
                "Racun stevilka: $id\n" +
                "Datum placila: $created\n\n" +
                "\t\t\t\tKolicina\tCena\n" +
                "$items\n" +
                "Popust: ${discount.multiply(BigDecimal(100)).setScale(2, RoundingMode.DOWN)}%\n" +
                "Za placilo brez DDV: ${totalBeforeTax.setScale(2, RoundingMode.UP)}\n" +
                "Za placilo skupaj(${
                    taxRate.multiply(BigDecimal(100)).setScale(2, RoundingMode.DOWN)
                }% DDV): ${totalAfterTax.setScale(2, RoundingMode.UP)}EUR\n\n" +
                "Blagajna: ${company.cashRegister}\n" +
                "Nacin placila: $paymentMethod \n" +
                "Blagajnik: ${company.cashierFullName}\n\n" +
                "\t\t\tHvala za obisk!"
    }
}