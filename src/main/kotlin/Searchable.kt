import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

interface Searchable {
    fun search(searchText: String, classType: Any): Boolean {
        val properties = classType::class.declaredMemberProperties
        for (property in properties) {
            try{
                property.isAccessible = true
                val value = property.call(classType)
                if(value.toString().contains(searchText)) {
                    return true
                }
            } catch (ex: Exception) {
                print(ex.message)
            }
        }
        return false
    }
}