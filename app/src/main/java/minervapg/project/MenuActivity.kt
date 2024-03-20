package minervapg.project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import minervapg.project.R.*
import minervapg.project.imc_calculator.ImcCalculatorActivity
import minervapg.project.settings.SettingsActivity
import minervapg.project.superheroapp.SuperHeroListActivity
import minervapg.project.todoapp.TodoActivity


class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_menu)
        val btnHelloApp = findViewById<Button>(id.btnHelloApp)
        val btnImcApp = findViewById<Button>(id.btnIMCApp)
        val btnTODO = findViewById<Button>(id.btnTODO)
        val btnSuperHeroApp = findViewById<Button>(id.btnSuperHeroApp)
        val btnSettings = findViewById<Button>(id.btnSettings)

        btnHelloApp.setOnClickListener { navigateToHelloApp() }
        btnImcApp.setOnClickListener { navigateToImcApp() }
        btnTODO.setOnClickListener { navigateTODOApp() }
        btnSuperHeroApp.setOnClickListener { navigateToSuperHeroApp() }
        btnSettings.setOnClickListener { navigateToSettings() }

    }

    private fun navigateToSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToSuperHeroApp(){
        val intent = Intent(this, SuperHeroListActivity::class.java)
        startActivity(intent)

    }

    private fun navigateTODOApp() {
        val intent = Intent(this, TodoActivity::class.java)
        startActivity(intent)
    }


    private fun navigateToImcApp() {
        val intent = Intent(this, ImcCalculatorActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToHelloApp(){
        val intent = Intent(this, FirstAppActivity::class.java)
        startActivity(intent)
    }

}