package com.note.notes.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.note.notes.R

class SettingsFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}