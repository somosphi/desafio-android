package joao.moreno.phi_test.repository

import joao.moreno.phi_test.model.statement.StatementDetaiVO
import io.reactivex.Observable
import joao.moreno.phi_test.model.balance.BalanceVO
import joao.moreno.phi_test.model.statement.StatementVO
import retrofit2.http.GET
import retrofit2.http.Path

interface PhiService {
    @GET("myBalance")
    fun getBalance() : Observable<BalanceVO>

    @GET("myStatement/{limit}/{offset}")
    fun getStatement(@Path("limit") limit : String, @Path("offset") offset : String) : Observable<StatementVO>

    @GET("myStatement/detail/{id}")
    fun getDetailStatement(@Path("id") id : String) : Observable<StatementDetaiVO>
}