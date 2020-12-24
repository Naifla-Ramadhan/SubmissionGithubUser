package com.naifla.submissiongithubuser.fragment


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.naifla.submissiongithubuser.alarm.AlarmReceiver
import com.naifla.submissiongithubuser.R

/**
 * A simple [Fragment] subclass.
 */
class SettingFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var REMINDER : String
    private lateinit var LANGUAGE : String
    private lateinit var alarmReceiver : AlarmReceiver

    private lateinit var setReminder : SwitchPreference
    private lateinit var changeLanguage  : Preference

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        alarmReceiver = AlarmReceiver()
        if (key == REMINDER){
            setReminder.isChecked = sharedPreferences.getBoolean(REMINDER,false)

            if (setReminder.isChecked == true){
                val repeatAlarm = "02:26"
                val repeatMessage = "testestes"
                alarmReceiver.setRepeatingAlarm(context,repeatAlarm,repeatMessage)
                Toast.makeText(context,"data true",Toast.LENGTH_SHORT).show()
            }else{
                alarmReceiver.cancelingAlarm(context)
            }
        }



    }


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        init()
        setSumaries()
    }


    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun init(){
        REMINDER = resources.getString(R.string.key_reminder)
        LANGUAGE = resources.getString(R.string.key_language)

        setReminder = findPreference<SwitchPreference>(REMINDER) as SwitchPreference
        changeLanguage = findPreference<Preference>(LANGUAGE) as Preference
    }

    private fun setSumaries(){
        val sh  = preferenceManager.sharedPreferences
        setReminder.isChecked = sh.getBoolean(REMINDER,false)

        val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        changeLanguage.intent = intent
    }


}
