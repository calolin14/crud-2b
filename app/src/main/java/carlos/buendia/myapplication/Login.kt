package carlos.buendia.myapplication

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //1- Mandamos a traer a todos los elementos de la vista
        val txtcorreoL = findViewById<EditText>(R.id.txtCorreoL)
        val txtcontrasenaL = findViewById<EditText>(R.id.txtContrasenaL)
        val btnIngresarL = findViewById<Button>(R.id.btningresarR)
        val btnRegistrarse = findViewById<Button>(R.id.btnPantallaregistrar)


        //2- Programo los botones
        btnIngresarL.setOnClickListener {
            //preparo el intent para cambiar a la pantalla de bienvenida
            val pantallacrud = Intent(this, PantallaCrud::class.java)
            //Dentro de una corrutina hago un select en la base de datos
            GlobalScope.launch(Dispatchers.IO) {
                try
                {
                    //1-Creo un objeto de la clase conexion
                    val objConexion = ClaseConexion()?.cadenaConexion()
                    val comprobarUsuario = objConexion?.prepareStatement("SELECT * FROM usuarioHD WHERE Correo = ? AND Password = ?")!!
                    comprobarUsuario.setString(1, txtcorreoL.text.toString())
                    comprobarUsuario.setString(2, txtcontrasenaL.text.toString())
                    val resultado = comprobarUsuario.executeQuery()
                    //Si encuentra un resultado
                    if (resultado.next()) {
                        startActivity(pantallacrud)
                    } else {
                        println("Usuario no encontrado, verifique las credenciales")

                    }
                }
                catch (e: Exception){
                    println("error: $e")
                }
            }


        }
        btnRegistrarse.setOnClickListener {
            //Cambio de pantalla
            val pantallaRegistrarme = Intent(this, Registrarse::class.java)
            startActivity(pantallaRegistrarme)
        }


    }
}