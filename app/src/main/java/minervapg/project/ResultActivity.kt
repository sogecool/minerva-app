package minervapg.project

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import minervapg.project.R.id
import minervapg.project.R.layout


class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_result)
        val tvResult = findViewById<TextView>(id.tvResult)
        val name: String = intent.extras?.getString("EXTRA_NAME").orEmpty()
        tvResult.text = "Hola $name"
    }
}