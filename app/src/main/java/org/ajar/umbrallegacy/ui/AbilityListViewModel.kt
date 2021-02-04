package org.ajar.umbrallegacy.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import org.ajar.umbrallegacy.content.AbilityDAO
import org.ajar.umbrallegacy.content.UmbralDatabase
import org.ajar.umbrallegacy.model.Ability

class AbilityListViewModel(application: Application) : AndroidViewModel(application) {

    private val abilityDao: AbilityDAO
    val abilityLD: LiveData<List<Ability>>
    var selectedAbility: Ability? = null

    init {
        UmbralDatabase.init(application)
        abilityDao = UmbralDatabase.abilities!!
        abilityLD  = abilityDao.observeAllByName()
    }

    fun commitSelected() {
        selectedAbility?.also { abilityDao.insert(it) }
    }
}