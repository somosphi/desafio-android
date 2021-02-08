package joao.moreno.phi_test.model.statement

import java.text.SimpleDateFormat
import java.util.*

data class StatementDetaiVO(
    var amount: Double?,
    var authentication: String?,
    var createdAt: Date?,
    var description: String?,
    var id: String?,
    var tType: StatementType,
    var to: String?,


    ) {
    fun getDateFormated() : String = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(createdAt)
    fun getBank() : String {
        return "Banco Phi"
    }
}