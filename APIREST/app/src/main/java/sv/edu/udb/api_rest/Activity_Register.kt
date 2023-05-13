package sv.edu.udb.api_rest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class Activity_Register : AppCompatActivity() {

    // Creamos la referencia del objeto FirebaseAuth
    private lateinit var auth: FirebaseAuth
    //Referencia a componentes de nuestro Layout
    private lateinit var buttonRegister: Button
    private lateinit var textViewLogin: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth= FirebaseAuth.getInstance()

        buttonRegister=findViewById<Button>(R.id.btnRegistro)
        buttonRegister.setOnClickListener {
            val email=findViewById<EditText>(R.id.txtEmail).text.toString()
            val password =findViewById<EditText>(R.id.txtPass).text.toString()
            this.register(email,password)
        }

        textViewLogin=findViewById(R.id.textViewLogin)
        textViewLogin.setOnClickListener{
            this.goToLogin()
        }
    }

    private fun register(email:String, password: String){
      auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task->
          if(task.isSuccessful){
              val intent = Intent(this,MainActivity::class.java)
              startActivity(intent)
              finish()
          }
      }.addOnFailureListener{exception->
          Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_SHORT).show()
      }
    }

    private fun goToLogin(){
        val intent=Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }
}