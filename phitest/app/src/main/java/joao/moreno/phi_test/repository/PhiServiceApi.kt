package joao.moreno.phi_test.repository

import com.google.gson.GsonBuilder
import io.reactivex.Observable
import joao.moreno.phi_test.model.statement.StatementDetaiVO
import joao.moreno.phi_test.model.balance.BalanceVO
import joao.moreno.phi_test.model.statement.StatementVO
import joao.moreno.phi_test.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PhiServiceApi {
    val service: PhiService

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder().apply {
            addInterceptor {
                val request: Request = it.request().newBuilder().addHeader("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c").build()
                it.proceed(request)
            }
            addInterceptor(logging)
        }

        val gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()

        service = retrofit.create(PhiService::class.java)
    }

    fun getBalance() : Observable<BalanceVO> = service.getBalance()

    fun getStatement(limit : String, offset : String) : Observable<StatementVO> = service.getStatement(limit, offset)

    fun getStatementDetail(transactionId : String) : Observable<StatementDetaiVO> = service.getDetailStatement(transactionId)

}
