import java.math.BigDecimal
import java.util.*

class Company(
    var name: String,
    var address: String,
    var mobileNumber: String,
    var taxNumber: BigDecimal,
    var registrationNumber: String,
    var accountNumber: String
) : Searchable {
    var id = UUID.randomUUID()
    var cashierFullName: String = "Rene Jausovec"
    var cashRegister: String = "Blagajna1"
    var email = "company@test.com"


    override fun toString(): String {
        return "\t\t\t\t$name\n" +
                "Naslov: $address\n" +
                "\tTelefonska stevilka: $mobileNumber\n"
    }
}