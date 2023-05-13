package sv.edu.udb.api_rest.Vista

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sv.edu.udb.api_rest.Controlador.Alumno
import sv.edu.udb.api_rest.Controlador.Profesor
import sv.edu.udb.api_rest.Modelo.AlumnoApi
import sv.edu.udb.api_rest.Modelo.ProfeApi
import sv.edu.udb.api_rest.Nucleo.RetrofitHelper
import sv.edu.udb.api_rest.R

class Activity_Crear_Profesor : AppCompatActivity() {
    private lateinit var nombreEditText: EditText
    private lateinit var apellidoEditText: EditText
    private lateinit var edadEditText: EditText
    private lateinit var materiaEditText: EditText
    private lateinit var crearButton: Button
    private lateinit var api: ProfeApi
    private lateinit var profesor: Profesor

    // Obtener las credenciales de autenticación
    var auth_username = ""
    var auth_password = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_profesor)

        val datos: Bundle? = intent.getExtras()
        if (datos != null) {
            auth_username = datos.getString("auth_username").toString()
            auth_password = datos.getString("auth_password").toString()
        }

        nombreEditText = findViewById(R.id.editTextNombre_prof)
        apellidoEditText = findViewById(R.id.editTextApellido_prof)
        edadEditText = findViewById(R.id.editTextEdad_prof)
        materiaEditText = findViewById(R.id.editTextMateria_prof)
        crearButton = findViewById(R.id.btnGuardar_prof)

        crearButton.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val apellido = apellidoEditText.text.toString()
            val edad = edadEditText.text.toString().toInt()
            val materia = materiaEditText.text.toString()

            profesor = Profesor(0, nombre, apellido, edad, materia)
            Log.e("API", "auth_username: $auth_username")
            Log.e("API", "auth_password: $auth_password")
            api = RetrofitHelper.getRetro().create(ProfeApi::class.java)

            Add(api)
        }
    }

    private fun Add(api: ProfeApi){
        api.CrearProfesor(profesor).enqueue(object : Callback<Profesor> {
            override fun onResponse(call: Call<Profesor>, response: Response<Profesor>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@Activity_Crear_Profesor, "Profesor creado exitosamente", Toast.LENGTH_SHORT).show()
                    val i = Intent(getBaseContext(), Activity_Profesor_Main::class.java)
                    startActivity(i)
                } else {
                    val error = response.errorBody()?.string()
                    Log.e("API", "Error crear docente: $error")
                    Toast.makeText(this@Activity_Crear_Profesor, "Error al crear el docente", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<Profesor>, t: Throwable) {
                Toast.makeText(this@Activity_Crear_Profesor, "Error al crear el docente", Toast.LENGTH_SHORT).show()
                Log.e("tag", "error", t)
            }
        })
    }
}