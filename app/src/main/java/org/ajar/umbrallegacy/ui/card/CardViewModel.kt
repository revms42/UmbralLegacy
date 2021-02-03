package org.ajar.umbrallegacy.ui.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.ajar.umbrallegacy.R
import org.ajar.umbrallegacy.content.AbilityDatabase
import org.ajar.umbrallegacy.model.Card
import org.ajar.umbrallegacy.model.Image
import org.ajar.umbrallegacy.model.PrincipleAbilityType

class CardViewModel : ViewModel() {

    private var _card: Card? = null
    var card: Card?
        get() = _card
        set(value) {
            _card = value.also {
                if (it == null) {
                    _cardImage.postValue(nullIconImage)
                    _factionImage.postValue(null)
                    _cardText.postValue("")
                    _flavorText.postValue("")
                    _cost.postValue(emptyList())
                    _costType.postValue(emptyList())
                    _archetypeImage.postValue(null)
                    _abilityImages.postValue(emptyList())
                    _showAbilityIcons.postValue(false)
                    _showRightCostBar.postValue(false)
                    _showLeftCostBar.postValue(false)
                    TODO("Show attack and defense icons as well, and trigger setting the layout.")
                } else {
                    _cardImage.postValue(it.cardImage)
                    _factionImage.postValue(it.faction?.groupIcon) //TODO: Replace with faction image when available
                    _cardText.postValue(it.cardText)
                    _flavorText.postValue(it.flavorText)

                    it.cost.filterNotNull().also { costList ->
                        _cost.postValue(costList.map { cost -> cost.icon })
                        _costType.postValue(costList.map { cost -> cost.costIcon })
                    }
                    it.abilities.mapNotNull {
                            ability -> ability?.let { id -> AbilityDatabase.abilities?.findById(id)}
                    }.also { abilities ->
                        _archetypeImage.postValue(abilities.firstOrNull { ability -> ability.type == PrincipleAbilityType.ARCHETYPE }?.icon)
                        _abilityImages.postValue(abilities.map { ability -> ability.icon })
                    }
                    _costType.postValue(it.costType.mapNotNull { costType -> costType?.costIcon })
                    _showAbilityIcons.postValue(it.showAbilityIcons)
                    _showRightCostBar.postValue(it.showRightCostBar)
                    _showLeftCostBar.postValue(it.showLeftCostBar)
                    TODO("Show attack and defense icons as well, and trigger setting the layout")
                }
            }
        }

    private val _cardImage = MutableLiveData<Image?>()
    val cardImage: LiveData<Image?> = _cardImage

    private val _factionImage = MutableLiveData<Image?>()
    val factionImage: LiveData<Image?> = _factionImage

    private val _cardText = MutableLiveData<String>()
    val cardText: LiveData<String> = _cardText

    private val _flavorText = MutableLiveData<String>()
    val flavorText: LiveData<String> = _flavorText

    private val _cost = MutableLiveData<List<Image>>()
    val cost: LiveData<List<Image>> = _cost

    private val _costType = MutableLiveData<List<Image>>()
    val costType: LiveData<List<Image>> = _costType

    private val _archetypeImage = MutableLiveData<Image?>()
    val archetypeImage: LiveData<Image?> = _archetypeImage

    private val _abilityImages = MutableLiveData<List<Image?>>()
    val abilityImages: LiveData<List<Image?>> = _abilityImages

    private val _showRightCostBar = MutableLiveData<Boolean>()
    val showRightCostBar: LiveData<Boolean> = _showRightCostBar

    private val _showLeftCostBar = MutableLiveData<Boolean>()
    val showLeftCostBar: LiveData<Boolean> = _showLeftCostBar

    private val _showAbilityIcons = MutableLiveData<Boolean>()
    val showAbilityIcons: LiveData<Boolean> = _showAbilityIcons

    //TODO: Need attack and defense icons here.
    //TODO: Need to have the layout here as well as some way to set and reset the layout.

    companion object {
        private val nullIconImage = Image(R.drawable.ic_invalid)
    }
}