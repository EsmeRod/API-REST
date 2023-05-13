package sv.edu.udb.api_rest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class SplashActivity : AppCompatActivity() {
    private lateinit var imagen: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        imagen=findViewById(R.id.imgV)
        imagen.alpha=0f
        imagen.animate().setDuration(2000).alpha(1f).withEndAction{
            val i=Intent(this,Activity_Register::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }

    }
}