package sv.edu.udb.api_rest.Modelo

import retrofit2.Call
import retrofit2.http.*
import sv.edu.udb.api_rest.Controlador.Alumno

interface AlumnoApi {

    @GET("alumno.php")
    fun obtenerAlumnos(): Call<List<Alumno>>

    @POST("alumno.php")
    fun CreateAlumno(@Body alumno: Alumno) : Call<Alumno>

    @PUT("alumno.php/{id}")
    fun UpdateAlumno(@Path("id") id: Int, @Body alumno: Alumno): Call<Alumno>

    @PUT("alumno.php/{id}")
    fun DeleteAlumno(@Path("id") id: Int, @Body alumno: Alumno): Call<Void>
}