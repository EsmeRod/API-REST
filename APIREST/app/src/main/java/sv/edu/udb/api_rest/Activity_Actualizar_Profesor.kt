package sv.edu.udb.api_rest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

class Activity_Actualizar_Profesor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_profesor)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.Alumno->{ Toast.makeText( this,"Selecciono Alumno", Toast.LENGTH_SHORT).show();
                val intent = Intent(this, Activity_Alumno_Main::class.java)
                startActivity(intent)}
            R.id.Profesores->{ Toast.makeText( this,"Selecciono Profesores", Toast.LENGTH_SHORT).show();
                val intent = Intent(this, Activity_Profesor_Main::class.java)
                startActivity(intent)}
        }
        return super.onOptionsItemSelected(item)
    }
}