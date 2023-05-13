package sv.edu.udb.api_rest.Nucleo

import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val URL = "http://192.168.0.9/API/"
    private val auth_username = "admin"
    private val auth_password = "admin123"
    val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", Credentials.basic(auth_username,
                    auth_password))
                .build()
            chain.proceed(request)
        }
        .build()

    fun getRetro(): Retrofit {
        return Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create())
            .client(client).build()
    }
}