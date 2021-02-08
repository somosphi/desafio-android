package joao.moreno.phi_test.model.statement

import java.io.Serializable
import java.util.*

data class ItemStatementVO(
    var amount: Double?,
    var bankName: String?,
    var createdAt: Date?,
    var description: String?,
    var from: String?,
    var id: String?,
    var tType: StatementType?,
    var to: String?
) : Serializable