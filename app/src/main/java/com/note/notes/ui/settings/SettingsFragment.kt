package com.note.notes.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.note.notes.R
import com.note.notes.ui.NotesActivity
import timber.log.Timber

class SettingsFragment: PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when(key){
            getString(R.string.switch_theme_key) -> {
                val enabled = sharedPreferences?.let {
                    sharedPreferences.getBoolean(key, false)
                }!!
                sharedPreferences.edit().putBoolean(getString(R.string.switch_theme_key), enabled).apply()
                (requireActivity() as NotesActivity).enableDarkTheme(enabled)
            }
        }
    }

}