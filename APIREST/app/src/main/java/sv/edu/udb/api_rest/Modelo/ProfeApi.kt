package sv.edu.udb.api_rest.Modelo

import retrofit2.Call
import retrofit2.http.*
import sv.edu.udb.api_rest.Controlador.Profesor

interface ProfeApi {

    @GET("profesor.php")
    fun obtenerProfesores(): Call<List<Profesor>>

    @POST("profesor.php")
    fun CrearProfesor(@Body profesor: Profesor) : Call<Profesor>

    @PUT("profesor.php/{id}")
    fun UpdateProfe(@Path("id") id: Int, @Body profesor: Profesor): Call<Profesor>

    @PUT("profesor.php/{id}")
    fun DeleteProfe(@Path("id") id: Int, @Body profesor: Profesor): Call<Void>
}