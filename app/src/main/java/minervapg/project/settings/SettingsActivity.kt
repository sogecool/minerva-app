package minervapg.project.settings

import android.app.UiModeManager.MODE_NIGHT_NO
import android.app.UiModeManager.MODE_NIGHT_YES
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import minervapg.project.databinding.ActivitySettingsBinding


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsActivity : AppCompatActivity() {

    companion object {
        const val VOLUME_VAL = "volume_lvl"
        const val KEY_BLUETOOTH = "switch_bluetooth"
        const val KEY_VIBRATION = "switch_vibration"
        const val KEY_DARK_MODE = "switch_dark_mode"
    }

    private lateinit var binding: ActivitySettingsBinding
    private var firstTime = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        CoroutineScope(Dispatchers.IO).launch {
            getSettings().filter { firstTime }.collect { settingsModel ->
                if (settingsModel != null) {
                    runOnUiThread{
                        binding.switchBluetooth.isChecked = settingsModel.bluetooth
                        binding.switchVibrar.isChecked = settingsModel.vibration
                        binding.switchDarkmode.isChecked = settingsModel.darkMode
                        binding.rsVolume.setValues(settingsModel.volume.toFloat())
                        firstTime = !firstTime
                    }
                }
            }
        }

        initUI()

    }

    private fun initUI() {
        binding.rsVolume.addOnChangeListener { _, value, _ ->
            Log.i("Aaron", "El valor es :$value")
            CoroutineScope(Dispatchers.IO).launch {
                saveVolumen(value.toInt())
            }
        }

        binding.switchBluetooth.setOnCheckedChangeListener { _, value ->
            CoroutineScope(Dispatchers.IO).launch {
                saveOptions(KEY_BLUETOOTH, value)
            }

        }

        binding.switchVibrar.setOnCheckedChangeListener { _, value ->
            CoroutineScope(Dispatchers.IO).launch {
                saveOptions(KEY_VIBRATION, value)
            }

        }

        binding.switchDarkmode.setOnCheckedChangeListener { _, value ->

            if(value){
                enableDarkMode()
            }else{
                disableDarkMode()
            }

            CoroutineScope(Dispatchers.IO).launch {
                saveOptions(KEY_DARK_MODE, value)
            }

        }
    }

    private suspend fun saveVolumen(value: Int) {
        dataStore.edit { preferences ->
            preferences[intPreferencesKey(VOLUME_VAL)] = value
        }
    }

    private suspend fun saveOptions(key: String, value: Boolean) {
        dataStore.edit { preference ->
            preference[booleanPreferencesKey(key)] = value
        }
    }

    private fun getSettings(): Flow<SettingsModel?> {
        return dataStore.data.map { preferences ->
            SettingsModel(
                volume = preferences[intPreferencesKey(VOLUME_VAL)] ?: 50,
                bluetooth = preferences[booleanPreferencesKey(KEY_BLUETOOTH)] ?: true,
                vibration = preferences[booleanPreferencesKey(KEY_VIBRATION)] ?: true,
                darkMode = preferences[booleanPreferencesKey(KEY_DARK_MODE)] ?: false
            )
        }

    }

    private fun enableDarkMode(){
        AppCompatDelegate.setDefaultNightMode(/* mode = */ AppCompatDelegate.MODE_NIGHT_YES)
        delegate.applyDayNight()
    }

    private fun disableDarkMode(){
        AppCompatDelegate.setDefaultNightMode(/* mode = */ AppCompatDelegate.MODE_NIGHT_NO)
        delegate.applyDayNight()
    }

}


