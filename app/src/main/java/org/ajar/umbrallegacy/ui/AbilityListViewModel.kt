package org.ajar.umbrallegacy.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import org.ajar.umbrallegacy.content.AbilityDAO
import org.ajar.umbrallegacy.content.AbilityDatabase
import org.ajar.umbrallegacy.model.Ability

class AbilityListViewModel(application: Application) : AndroidViewModel(application) {

    private val abilityDao: AbilityDAO
    val abilityLD: LiveData<List<Ability>>
    var selectedAbility: Ability? = null

    init {
        AbilityDatabase.init(application)
        abilityDao = AbilityDatabase.instance!!.abilityDao()
        abilityLD  = abilityDao.observeAllByName()
    }
}