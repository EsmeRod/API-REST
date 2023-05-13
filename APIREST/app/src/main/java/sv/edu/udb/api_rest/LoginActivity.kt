package sv.edu.udb.api_rest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    // Creamos la referencia del objeto FirebaseAuth
    private lateinit var auth: FirebaseAuth
    //Referencia a componentes de nuestro Layout
    private lateinit var btnLogin: Button
    private lateinit var textViewRegister: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth= FirebaseAuth.getInstance()

        btnLogin=findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            val email=findViewById<EditText>(R.id.editTxtEmail).text.toString()
            val password =findViewById<EditText>(R.id.txtPassword).text.toString()
            this.login(email,password)
        }

        textViewRegister=findViewById(R.id.textViewRegister)
        textViewRegister.setOnClickListener{
            this.goToRegister()
        }
    }

    private fun login(email:String, password: String){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{task->
            if(task.isSuccessful){
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener{exception->
            Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToRegister(){
        val intent= Intent(this,Activity_Register::class.java)
        startActivity(intent)}
}