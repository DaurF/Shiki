package mob.dau.ren.shiki.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.concurrent.Flow

private const val LIST_PREFERENCES_NAME = "list_preferences"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = LIST_PREFERENCES_NAME
)

/*
class SettingsDataStore(preferences_datastore: DataStore<Preferences>) {
    private val STATUSES = stringSetPreferencesKey("statuses")

    val preferenceFlow: Flow<Set<String>> = preferences_datastore.data
        .catch {
            if(it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {  preferences ->
            preferences[STATUSES] ?: emptySet()
        }

    suspend fun saveLayoutToPreferencesStore(statuses: Set<String>, context: Context) {
        context.dataStore.edit { preferences ->
            preferences[STATUSES] = statuses
        }
    }
}*/