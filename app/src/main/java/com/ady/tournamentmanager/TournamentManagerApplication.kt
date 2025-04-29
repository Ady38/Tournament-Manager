package com.ady.tournamentmanager

import android.app.Application
import com.ady.tournamentmanager.data.AppContainer
import com.ady.tournamentmanager.data.AppDataContainer

class TournamentManagerApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
