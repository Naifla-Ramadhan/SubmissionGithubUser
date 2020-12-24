package com.naifla.submissiongithubuser.room

import android.app.Application
import com.facebook.stetho.Stetho

class FavoriteApps : Application(){

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}
