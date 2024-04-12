import java.math.BigDecimal

enum class PaymentMethod {
    Kartica, Gotovina
}

fun main () {
    val invoice = Invoice(
        PaymentMethod.Kartica,
        BigDecimal(0.2),
        customer = Company(
            "Rene d.o.o", "Gasterajska cesta 23", "051235432",
            BigDecimal(0.95), "3231738", "SI5648732974328472"))

    print(invoice.toString())

    println()
    if(invoice.search("Rene", invoice)) {
        println("True")
    }
    else { println("False") }
}