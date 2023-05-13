package sv.edu.udb.api_rest.Vista

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sv.edu.udb.api_rest.Controlador.Alumno
import sv.edu.udb.api_rest.Controlador.Profesor
import sv.edu.udb.api_rest.Modelo.AlumnoApi
import sv.edu.udb.api_rest.Modelo.ProfeApi
import sv.edu.udb.api_rest.Nucleo.RetrofitHelper
import sv.edu.udb.api_rest.R

class Activity_Profesor_Main : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Activity_Profesor_Item
    private lateinit var api: ProfeApi
    private val auth_username = "admin"
    private val auth_password = "admin123"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profesor_main)

        recyclerView = findViewById(R.id.recyclerView_prof)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val fab_agregar: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fab_agregar_prof)

        api = RetrofitHelper.getRetro().create(ProfeApi::class.java)

        cargarDatos(api)

        fab_agregar.setOnClickListener(View.OnClickListener {
            val i = Intent(getBaseContext(), Activity_Crear_Profesor::class.java)
            i.putExtra("auth_username", auth_username)
            i.putExtra("auth_password", auth_password)
            startActivity(i)
        })
    }

    private fun cargarDatos(api: ProfeApi){
        val call = api.obtenerProfesores()
        call.enqueue(object : Callback<List<Profesor>> {
            override fun onResponse(call: Call<List<Profesor>>, response: Response<List<Profesor>>) {
                if (response.isSuccessful) {
                    val profesores = response.body()
                    if (profesores != null) {
                        adapter = Activity_Profesor_Item(profesores)
                        recyclerView.adapter = adapter

                        // Establecemos el escuchador de clics en el adaptador
                        adapter.setOnItemClickListener(object : Activity_Profesor_Item.OnItemClickListener {
                            override fun onItemClick(profesor: Profesor) {
                                val opciones = arrayOf("Modificar Docente", "Eliminar Docente")

                                AlertDialog.Builder(this@Activity_Profesor_Main)
                                    .setTitle(profesor.nombre)
                                    .setItems(opciones) { dialog, index ->
                                        when (index) {
                                            0 -> Modificar(profesor)
                                            1 -> EliminarProfe(profesor, api)
                                        }
                                    }
                                    .setNegativeButton("Cancelar", null)
                                    .show()
                            }
                        })
                    }
                } else {
                    val error = response.errorBody()?.string()
                    Log.e("API", "Error al obtener los docentes: $error")
                    Toast.makeText(
                        this@Activity_Profesor_Main,
                        "Error al obtener los docentes 1",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Profesor>>, t: Throwable) {
                Log.e("API", "Error al obtener los docentes: ${t.message}")
                Toast.makeText(
                    this@Activity_Profesor_Main,
                    "Error al obtener los docentes 2",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun Modificar(profesor: Profesor) {
        // Creamos un intent para ir a la actividad de actualización de alumnos
        val i = Intent(getBaseContext(), Activity_Actualizar_Profesor::class.java)
        // Pasamos el ID del alumno seleccionado a la actividad de actualización
        i.putExtra("alumno_id", profesor.id)
        i.putExtra("nombre", profesor.nombre)
        i.putExtra("apellido", profesor.apellido)
        i.putExtra("edad", profesor.edad)
        i.putExtra("materia", profesor.materia)
        // Iniciamos la actividad de actualización de alumnos
        startActivity(i)
    }

    private fun EliminarProfe(profesor: Profesor, api: ProfeApi) {
        val profes = Profesor(profesor.id,"", "", -987, "")
        Log.e("API", "id : $profesor")
        val llamada = api.DeleteProfe(profesor.id, profes)
        llamada.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@Activity_Profesor_Main, "Profesor eliminado", Toast.LENGTH_SHORT).show()
                    cargarDatos(api)
                } else {
                    val error = response.errorBody()?.string()
                    Log.e("API", "Error al eliminar docente : $error")
                    Toast.makeText(this@Activity_Profesor_Main, "Error al eliminar docente 1", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("API", "Error al eliminar docente : $t")
                Toast.makeText(this@Activity_Profesor_Main, "Error al eliminar docente 2", Toast.LENGTH_SHORT).show()
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