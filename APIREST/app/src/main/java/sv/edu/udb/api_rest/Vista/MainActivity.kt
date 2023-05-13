package sv.edu.udb.api_rest.Vista

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import sv.edu.udb.api_rest.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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