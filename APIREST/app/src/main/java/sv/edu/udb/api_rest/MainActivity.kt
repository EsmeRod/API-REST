package sv.edu.udb.api_rest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.content.Intent

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
       R.id.Alumno-> Toast.makeText( this,"Selecciono Alumno", Toast.LENGTH_SHORT).show();
               //val intent = Intent(this, Opcion2Activity::class.java)
           //startActivity(intent)}
           R.id.Profesores-> Toast.makeText( this,"Selecciono Profesores", Toast.LENGTH_SHORT).show();
               //val intent = Intent(this, Opcion2Activity::class.java)
           //startActivity(intent)}
       }
        return super.onOptionsItemSelected(item)
    }
}