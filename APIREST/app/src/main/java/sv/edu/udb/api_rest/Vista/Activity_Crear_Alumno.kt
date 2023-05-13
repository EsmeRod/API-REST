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
import sv.edu.udb.api_rest.Modelo.AlumnoApi
import sv.edu.udb.api_rest.Nucleo.RetrofitHelper
import sv.edu.udb.api_rest.R

class Activity_Crear_Alumno : AppCompatActivity() {
    private lateinit var nombreEditText: EditText
    private lateinit var apellidoEditText: EditText
    private lateinit var edadEditText: EditText
    private lateinit var crearButton: Button
    private lateinit var api: AlumnoApi
    private lateinit var alumno: Alumno

    // Obtener las credenciales de autenticación
    var auth_username = ""
    var auth_password = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_alumno)

        // Obtención de datos que envia actividad anterior
        val datos: Bundle? = intent.getExtras()
        if (datos != null) {
            auth_username = datos.getString("auth_username").toString()
            auth_password = datos.getString("auth_password").toString()
        }

        nombreEditText = findViewById(R.id.editTextNombre)
        apellidoEditText = findViewById(R.id.editTextApellido)
        edadEditText = findViewById(R.id.editTextEdad)
        crearButton = findViewById(R.id.btnGuardar)

        crearButton.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val apellido = apellidoEditText.text.toString()
            val edad = edadEditText.text.toString().toInt()

            alumno = Alumno(0, nombre, apellido, edad)
            Log.e("API", "auth_username: $auth_username")
            Log.e("API", "auth_password: $auth_password")
            api = RetrofitHelper.getRetro().create(AlumnoApi::class.java)

            Add(api)
        }
    }

    private fun Add(api: AlumnoApi){
        api.CreateAlumno(alumno).enqueue(object : Callback<Alumno> {
            override fun onResponse(call: Call<Alumno>, response: Response<Alumno>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@Activity_Crear_Alumno, "Alumno creado exitosamente", Toast.LENGTH_SHORT).show()
                    val i = Intent(getBaseContext(), Activity_Alumno_Main::class.java)
                    startActivity(i)
                } else {
                    val error = response.errorBody()?.string()
                    Log.e("API", "Error crear alumno: $error")
                    Toast.makeText(this@Activity_Crear_Alumno, "Error al crear el alumno", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<Alumno>, t: Throwable) {
                Toast.makeText(this@Activity_Crear_Alumno, "Error al crear el alumno", Toast.LENGTH_SHORT).show()
            }
        })
    }
}