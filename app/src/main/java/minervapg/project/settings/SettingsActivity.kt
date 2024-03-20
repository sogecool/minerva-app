package minervapg.project.settings

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import minervapg.project.databinding.ActivitySettingsBinding


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsActivity : AppCompatActivity() {

    companion object{
        const val VOLUME_VAL = "volume_lvl"
    }

    private lateinit var binding: ActivitySettingsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()

    }

    private fun initUI() {
        binding.rsVolume.addOnChangeListener { _, value, _ ->
            Log.i("Aaron", "El valor es :$value")
            CoroutineScope(Dispatchers.IO).launch{
                 saveVolumen(value.toInt())
            }
        }
    }

    private suspend fun saveVolumen(value: Int) {
        dataStore.edit { preferences ->
            preferences[intPreferencesKey(VOLUME_VAL)] = value
        }
    }
}