package com.naifla.submissiongithubuser.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.naifla.submissiongithubuser.R
import com.naifla.submissiongithubuser.fragment.SettingFragment

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportFragmentManager.beginTransaction().add(
            R.id.settig_holder,
            SettingFragment()
        ).commit()
    }
}
