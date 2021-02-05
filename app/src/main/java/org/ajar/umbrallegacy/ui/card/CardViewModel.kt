package org.ajar.umbrallegacy.ui.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.ajar.umbrallegacy.R
import org.ajar.umbrallegacy.content.UmbralDatabase
import org.ajar.umbrallegacy.model.*

class CardViewModel : ViewModel() {

    private var _card: Card? = null
    var card: Card?
        get() = _card
        set(value) {
            _card = value.also {
                if (it == null) {
                    _cardName.postValue("")
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
                    _attackImage.postValue(AttributeImage.ZERO.attackImage)
                    _defenseImage.postValue(AttributeImage.ZERO.defenseImage)
                    _layout.postValue(CardLayout.IMAGE_TEXT_HORIZONTAL_SPLIT)
                } else {
                    _cardName.postValue(it.name)
                    _cardImage.postValue(it.cardImage)
                    _factionImage.postValue(it.faction?.groupIcon) //TODO: Replace with faction image when available
                    _cardText.postValue(it.cardText)
                    _flavorText.postValue(it.flavorText)

                    it.cost.filterNotNull().also { costList ->
                        _cost.postValue(costList.map { cost -> cost.icon })
                        _costType.postValue(costList.map { cost -> cost.costIcon })
                    }
                    it.abilities.mapNotNull {
                            ability -> ability?.let { id -> UmbralDatabase.abilities?.findById(id)}
                    }.also { abilities ->
                        _archetypeImage.postValue(abilities.firstOrNull { ability -> ability.type == PrincipleAbilityType.ARCHETYPE }?.icon)
                        _abilityImages.postValue(abilities.filter { ability ->
                                ability.type != PrincipleAbilityType.ARCHETYPE
                        }.map { ability -> ability.icon })
                    }
                    _costType.postValue(it.costType.mapNotNull { costType -> costType?.costIcon })
                    _showAbilityIcons.postValue(it.showAbilityIcons)
                    _showRightCostBar.postValue(it.showRightCostBar)
                    _showLeftCostBar.postValue(it.showLeftCostBar)
                    _attackImage.postValue(AttributeImage.forInt(it.attack).attackImage)
                    _defenseImage.postValue(AttributeImage.forInt(it.defense).defenseImage)
                    _layout.postValue(it.layout)
                }
            }
        }

    private val _layout = MutableLiveData<CardLayout>()
    val layoutLD: LiveData<CardLayout> = _layout
    var layout: CardLayout?
        get() = card?.layout
        set(value) {
            card?.also {
                it.layout = value?: CardLayout.IMAGE_TEXT_HORIZONTAL_SPLIT
                _layout.postValue(it.layout)
            }
        }

    private val _cardName = MutableLiveData<String>()
    val cardNameLD: LiveData<String> = _cardName
    var cardName: String
        get() = _card?.name?: ""
        set(value) {
            card?.also {
                it.name = value
                _cardName.postValue(it.name)
            }
        }

    private val _cardNameStyle = MutableLiveData<Int>()
    val cardNameStyleLD: LiveData<Int> = _cardNameStyle
    var cardNameStyle: Int
        get() = _card?.nameStyle?: R.style.TextView_CardName
        set(value) {
            card?.also {
                it.nameStyle = value
                _cardNameStyle.postValue(it.nameStyle)
            }
        }
    
    private val _cardBackground = MutableLiveData<Image?>()
    val cardBackgroundLD: LiveData<Image?> = _cardBackground
    var cardBackground: Image?
        get() = _card?.cardBackground
        set(value) {
            _card?.also { 
                it.cardBackground = value
                _cardBackground.postValue(it.cardBackground)
            }
        }

    private val _cardImage = MutableLiveData<Image?>()
    val cardImageLD: LiveData<Image?> = _cardImage
    var cardImage: Image?
        get() = card?.cardImage
        set(value) {
            card?.also {
                it.cardImage = value
                _cardImage.postValue(it.cardImage)
            }
        }
    
    private val _cardImageBackground = MutableLiveData<Image?>()
    val cardImageBackgroundLD: LiveData<Image?> = _cardImageBackground
    var cardImageBackground: Image?
        get() = card?.cardImageBackground
        set(value) {
            card?.also { 
                it.cardImageBackground = value
                _cardImageBackground.postValue(it.cardImageBackground)
            }
        }

    private val _factionImage = MutableLiveData<Image?>()
    val factionImageLD: LiveData<Image?> = _factionImage
    var faction: Faction?
        get() = card?.faction
        set(value) {
            card?.also {
                it.faction = value
                _factionImage.postValue(it.faction?.groupIcon) //TODO: Replace with faction image when available

                val group = Group.findGroup(it.faction)
                if(it.cardBackground == null || applyFactionStyles) _cardBackground.postValue(group.cardBackground)
                if(it.cardImageBackground == null || applyFactionStyles) _cardImageBackground.postValue(group.textBackground)
                if(it.cardTextBackground == null || applyFactionStyles) _cardTextBackground.postValue(group.textBackground)
                if(it.flavorTextBackground == null || applyFactionStyles) _flavorTextBackground.postValue(group.textBackground)
                if(it.nameStyle == null || applyFactionStyles) _cardNameStyle.postValue(group.nameStyle)
                if(it.cardTextStyle == null || applyFactionStyles) _cardTextStyle.postValue(group.textStyle)
                if(it.flavorTextStyle == null || applyFactionStyles) _flavorTextStyle.postValue(group.flavorStyle)
            }
        }

    var applyFactionStyles = true //TODO: Eventaully factions may have their own styles, right now we use group.

    private val _cardText = MutableLiveData<String>()
    val cardTextLD: LiveData<String> = _cardText
    var cardText: String?
        get() = card?.cardText
        set(value) {
            card?.also {
                it.cardText = value
                _cardText.postValue(it.cardText)
            }
        }

    private val _cardTextStyle = MutableLiveData<Int>()
    val cardTextStyleLD: LiveData<Int> = _cardTextStyle
    var cardTextStyle: Int
        get() = _card?.cardTextStyle?: R.style.TextView_CardName
        set(value) {
            card?.also {
                it.cardTextStyle = value
                _cardTextStyle.postValue(it.cardTextStyle)
            }
        }

    private val _cardTextBackground = MutableLiveData<Image?>()
    val cardTextBackgroundLD: LiveData<Image?> = _cardTextBackground
    var cardTextBackground: Image?
        get() = card?.cardTextBackground
        set(value) {
            card?.also {
                it.cardTextBackground = value
                _cardTextBackground.postValue(it.cardTextBackground)
            }
        }

    private val _flavorText = MutableLiveData<String>()
    val flavorTextLD: LiveData<String> = _flavorText
    var flavorText: String?
        get() = card?.flavorText
        set(value) {
            card?.also {
                it.flavorText = value
                _flavorText.postValue(it.flavorText)
            }
        }

    private val _flavorTextStyle = MutableLiveData<Int>()
    val flavorTextStyleLD: LiveData<Int> = _flavorTextStyle
    var flavorTextStyle: Int
        get() = _card?.flavorTextStyle?: R.style.TextView_CardName
        set(value) {
            card?.also {
                it.flavorTextStyle = value
                _flavorTextStyle.postValue(it.flavorTextStyle)
            }
        }

    private val _flavorTextBackground = MutableLiveData<Image?>()
    val flavorTextBackgroundLD: LiveData<Image?> = _flavorTextBackground
    var flavorTextBackground: Image?
        get() = card?.flavorTextBackground
        set(value) {
            card?.also {
                it.flavorTextBackground = value
                _flavorTextBackground.postValue(it.flavorTextBackground)
            }
        }

    private val _attackImage = MutableLiveData<Image>()
    val attackImageLD: LiveData<Image> = _attackImage
    var attack: Int
        get() = card?.attack?: 0
        set(value) {
            card?.also {
                it.attack = value
                _attackImage.postValue(AttributeImage.forInt(it.attack).attackImage)
            }
        }

    private val _defenseImage = MutableLiveData<Image>()
    val defenseImageLD: LiveData<Image> = _defenseImage
    var defense: Int
        get() = card?.defense?: 0
        set(value) {
            card?.also {
                it.defense = value
                _defenseImage.postValue(AttributeImage.forInt(it.defense).defenseImage)
            }
        }

    private val _cost = MutableLiveData<List<Image>>()
    val costLD: LiveData<List<Image>> = _cost
    var cost: List<Cost?>
        get() = card?.cost?: emptyList()
        set(value) {
            card?.also {
                it.cost = value
                _cost.postValue(it.cost.mapNotNull { cost -> cost?.icon })
            }
        }

    private val _costType = MutableLiveData<List<Image>>()
    val costTypeLD: LiveData<List<Image>> = _costType
    var costType: List<Cost?>
        get() = card?.costType?: emptyList()
        set(value) {
            card?.also {
                it.costType = value
                _costType.postValue(it.costType.mapNotNull { cost -> cost?.costIcon })
            }
        }

    private val _archetypeImage = MutableLiveData<Image?>()
    val archetypeImageLD: LiveData<Image?> = _archetypeImage

    private val _abilityImages = MutableLiveData<List<Image?>>()
    val abilityImagesLD: LiveData<List<Image?>> = _abilityImages
    var abilities: List<Ability>
        get() = card?.abilities?.mapNotNull { it?.let { id -> UmbralDatabase.abilities?.findById(id) } }?: emptyList()
        set(value) {
            card?.also {
                it.abilities = value.filter { ability -> ability.type != PrincipleAbilityType.ARCHETYPE }.map { ability -> ability.id }
                _abilityImages.postValue(value.map { ability -> ability.icon } )

                value.firstOrNull { ability -> ability.type == PrincipleAbilityType.ARCHETYPE }?.also { archetype ->
                    _archetypeImage.postValue(archetype.icon)
                }
            }
        }

    private val _showRightCostBar = MutableLiveData<Boolean>()
    val showRightCostBarLD: LiveData<Boolean> = _showRightCostBar
    var showRightCostBar: Boolean
        get() = card?.showRightCostBar?: false
        set(value) {
            card?.also {
                it.showRightCostBar = value
                _showRightCostBar.postValue(it.showRightCostBar)
            }
        }

    private val _showLeftCostBar = MutableLiveData<Boolean>()
    val showLeftCostBarLD: LiveData<Boolean> = _showLeftCostBar
    var showLeftCostBar: Boolean
        get() = card?.showLeftCostBar?: false
        set(value) {
            card?.also {
                it.showLeftCostBar = value
                _showLeftCostBar.postValue(it.showLeftCostBar)
            }
        }

    private val _showAbilityIcons = MutableLiveData<Boolean>()
    val showAbilityIconsLD: LiveData<Boolean> = _showAbilityIcons
    var showAbilityIcons: Boolean
        get() = card?.showAbilityIcons?: false
        set(value) {
            card?.also {
                it.showAbilityIcons = value
                _showAbilityIcons.postValue(it.showAbilityIcons)
            }
        }

    //TODO: Need some methods here for saving changes to cards.

    companion object {
        val nullIconImage = Image(R.drawable.ic_invalid)
        val nullBackground = Image(R.drawable.background_none)
    }
}