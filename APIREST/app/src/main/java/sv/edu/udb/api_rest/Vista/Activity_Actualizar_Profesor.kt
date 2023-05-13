package sv.edu.udb.api_rest.Vista

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sv.edu.udb.api_rest.Controlador.Alumno
import sv.edu.udb.api_rest.Controlador.Profesor
import sv.edu.udb.api_rest.Modelo.AlumnoApi
import sv.edu.udb.api_rest.Modelo.ProfeApi
import sv.edu.udb.api_rest.Nucleo.RetrofitHelper
import sv.edu.udb.api_rest.R

class Activity_Actualizar_Profesor : AppCompatActivity() {

    private lateinit var nombreEditText: EditText
    private lateinit var apellidoEditText: EditText
    private lateinit var edadEditText: EditText
    private lateinit var materiaEditText: EditText
    private lateinit var actualizarButton: Button
    private lateinit var docenteActualizado: Profesor
    private var docenteID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_profesor)

        nombreEditText = findViewById(R.id.nombreEditText_prof)
        apellidoEditText = findViewById(R.id.apellidoEditText_prof)
        edadEditText = findViewById(R.id.edadEditText_prof)
        materiaEditText = findViewById(R.id.materiaEditText_prof)
        actualizarButton = findViewById(R.id.actualizarButton_prof)

        val api = RetrofitHelper.getRetro().create(ProfeApi::class.java)

        // Obtener el ID del docente de la actividad anterior
        docenteID = intent.getIntExtra("alumno_id", -1)
        Log.e("API", "alumnoId : $docenteID")

        val nombre = intent.getStringExtra("nombre").toString()
        val apellido = intent.getStringExtra("apellido").toString()
        val edad = intent.getIntExtra("edad", 1)
        val materia = intent.getStringExtra("materia").toString()

        nombreEditText.setText(nombre)
        apellidoEditText.setText(apellido)
        edadEditText.setText(edad.toString())
        materiaEditText.setText(materia)

        val profesor = Profesor(0, nombre, apellido, edad, materia)

        actualizarButton.setOnClickListener {
            if (profesor != null) {
                // Crear un nuevo objeto docente con los datos actualizados
                docenteActualizado = Profesor(
                    docenteID,
                    nombreEditText.text.toString(),
                    apellidoEditText.text.toString(),
                    edadEditText.text.toString().toInt(),
                    materiaEditText.text.toString()
                )
                val jsonAlumnoActualizado = Gson().toJson(docenteActualizado)
                Log.d("API", "JSON enviado: $jsonAlumnoActualizado")

                val gson = GsonBuilder()
                    .setLenient() // Agrega esta línea para permitir JSON malformado
                    .create()

                Actualizar(api)
            }
        }
    }

    private fun Actualizar(api: ProfeApi){
        val call = api.UpdateProfe(docenteID, docenteActualizado).
        enqueue(object : Callback<Profesor> {
            override fun onResponse(call: Call<Profesor>, response: Response<Profesor>) {
                if (response.isSuccessful && response.body() != null) {
                    // Si la solicitud es exitosa, mostrar un mensaje de éxito en un Toast
                    Toast.makeText(this@Activity_Actualizar_Profesor, "Docente actualizado correctamente", Toast.LENGTH_SHORT).show()
                    val i = Intent(getBaseContext(), Activity_Profesor_Main::class.java)
                    startActivity(i)
                } else {
                    // Si la respuesta del servidor no es exitosa, manejar el error
                    try {
                        val errorJson = response.errorBody()?.string()
                        val errorObj = JSONObject(errorJson)
                        val errorMessage = errorObj.getString("message")
                        Toast.makeText(this@Activity_Actualizar_Profesor, errorMessage, Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        // Si no se puede parsear la respuesta del servidor, mostrar un mensaje de error genérico
                        Toast.makeText(this@Activity_Actualizar_Profesor, "Error al actualizar el docente", Toast.LENGTH_SHORT).show()
                        Log.e("API", "Error al parsear el JSON: ${e.message}")
                    }
                }
            }

            override fun onFailure(call: Call<Profesor>, t: Throwable) {
                // Si la solicitud falla, mostrar un mensaje de error en un Toast
                Log.e("API", "onFailure : $t")
                Toast.makeText(this@Activity_Actualizar_Profesor, "Error al actualizar el docente", Toast.LENGTH_SHORT).show()

                // Si la respuesta JSON está malformada, manejar el error
                try {
                    val gson = GsonBuilder().setLenient().create()
                    val error = t.message ?: ""
                    val alumno = gson.fromJson(error, Alumno::class.java)
                    // trabajar con el objeto Alumno si se puede parsear
                } catch (e: JsonSyntaxException) {
                    Log.e("API", "Error al parsear el JSON: ${e.message}")
                } catch (e: IllegalStateException) {
                    Log.e("API", "Error al parsear el JSON: ${e.message}")
                }
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.Alumno -> Alumno()
            R.id.Profesores -> Profesor()

        }
        return super.onOptionsItemSelected(item)
    }

    fun Alumno(){
        val alumno = Intent(this, Activity_Alumno_Main::class.java)
        startActivity(alumno)
    }

    fun Profesor(){
        val profesor = Intent(this, Activity_Profesor_Main::class.java)
        startActivity(profesor)
    }
}