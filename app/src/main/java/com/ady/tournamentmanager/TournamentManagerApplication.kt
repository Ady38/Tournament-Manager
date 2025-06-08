package com.ady.tournamentmanager

import android.app.Application
import com.ady.tournamentmanager.data.AppContainer
import com.ady.tournamentmanager.data.AppDataContainer

/**
 * Trieda ktora reprezentuje aplikaciu
 */
class TournamentManagerApplication : Application() {

    lateinit var container: AppContainer

    /**
     * Funkcia ktora sa vola pri vytvoreni aplikacie
     */
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
