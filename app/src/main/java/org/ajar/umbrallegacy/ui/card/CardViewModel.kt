package org.ajar.umbrallegacy.ui.card

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.*
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
                    _faction.postValue(null)
                    _cardText.postValue("")
                    _flavorText.postValue("")
                    _cost.postValue(emptyList())
                    _costType.postValue(emptyList())
                    _archetype.postValue(null)
                    _abilities.postValue(emptyList())
                    _showAbilityIcons.postValue(false)
                    _showRightCostBar.postValue(false)
                    _showLeftCostBar.postValue(false)
                    _attackImage.postValue(AttributeImage.ZERO.attackImage)
                    _defenseImage.postValue(AttributeImage.ZERO.defenseImage)
                    _layout.postValue(CardLayout.IMAGE_TEXT_HORIZONTAL_SPLIT)
                } else {
                    _cardName.postValue(it.name)
                    _cardImage.postValue(it.cardImage)
                    _faction.postValue(it.faction)
                    _cardText.postValue(it.cardText)
                    _flavorText.postValue(it.flavorText)

                    it.cost.filterNotNull().also { costList ->
                        _cost.postValue(costList.map { cost -> cost.icon })
                        _costType.postValue(costList.map { cost -> cost.costIcon })
                    }
                    it.abilities.mapNotNull {
                            ability -> ability?.let { id -> UmbralDatabase.abilities?.findById(id)}
                    }.also { abilities ->
                        _archetype.postValue(abilities.firstOrNull { ability -> ability.type == PrincipleAbilityType.ARCHETYPE })
                        _abilities.postValue(abilities.filter { ability ->
                                ability.type != PrincipleAbilityType.ARCHETYPE
                        })
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

    private val _faction = MutableLiveData<Faction?>()
    val factionLD: LiveData<Faction?> = _faction
    var faction: Faction?
        get() = card?.faction
        set(value) {
            card?.also {
                it.faction = value
                _faction.postValue(it.faction)

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

    private val _archetype = MutableLiveData<Ability?>()
    val archetypeLD: LiveData<Ability?> = _archetype

    private val _abilities = MutableLiveData<List<Ability?>>()
    val abilitiesLD: LiveData<List<Ability?>> = _abilities
    var abilities: List<Ability>
        get() = card?.abilities?.mapNotNull { it?.let { id -> UmbralDatabase.abilities?.findById(id) } }?: emptyList()
        set(value) {
            card?.also {
                it.abilities = value.filter { ability -> ability.type != PrincipleAbilityType.ARCHETYPE }.map { ability -> ability.id }
                _abilities.postValue(value)

                value.firstOrNull { ability -> ability.type == PrincipleAbilityType.ARCHETYPE }?.also { archetype ->
                    _archetype.postValue(archetype)
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
    
    fun <A> wireUpView(cardView: View, activity: A) where A: Context, A: LifecycleOwner{
        cardView.also { view ->
            val borderView = view.findViewById<View>(R.id.borderView)
            this.cardBackgroundLD.observe(activity, Observer {
                borderView.background = it?.getDrawable(activity.resources)?: nullBackground.getDrawable(activity.resources)
            })

            val cardTextView = view.findViewById<TextView>(R.id.cardText)
            this.cardTextLD.observe(activity, Observer {
                cardTextView.text = it
            })
            this.cardTextBackgroundLD.observe(activity, Observer {
                if(it != null) {
                    cardTextView.background = it.getDrawable(activity.resources)
                } else {
                    cardTextView.background = nullBackground.getDrawable(activity.resources)
                }
            })
            this.cardTextStyleLD.observe(activity, Observer {
                cardTextView.setTextAppearance(it)
            })

            val cardImageView = view.findViewById<ImageView>(R.id.cardImage)
            this.cardImageLD.observe(activity, Observer { image ->
                if(image != null) {
                    cardImageView.setImageDrawable(image.getDrawable(activity.resources))
                } else {
                    cardImageView.setImageDrawable(nullIconImage.getDrawable(activity.resources))
                }
            })

            val cardFlavorView = view.findViewById<TextView>(R.id.flavorText)
            this.flavorTextLD.observe(activity, Observer {
                cardFlavorView.text = it
            })
            this.flavorTextBackgroundLD.observe(activity, Observer {
                if(it != null) {
                    cardFlavorView.background = it.getDrawable(activity.resources)
                } else {
                    cardFlavorView.background = CardViewModel.nullBackground.getDrawable(activity.resources)
                }
            })
            this.flavorTextStyleLD.observe(activity, Observer {
                cardFlavorView.setTextAppearance(it)
            })

            val factionImageView = view.findViewById<ImageView>(R.id.factionImage)
            this.factionLD.observe(activity, Observer { faction ->
                if(faction != null) {
                    factionImageView.setImageDrawable(faction.ability.icon.getDrawable(activity.resources))  //TODO: Replace with faction image when available
                    factionImageView.visibility = View.VISIBLE
                    factionImageView.contentDescription = activity.getString(R.string.content_desc_faction_image, activity.getString(faction.factionName))
                } else {
                    factionImageView.visibility = View.INVISIBLE
                }
            })

            val archetypeImageView = view.findViewById<ImageView>(R.id.archetypeImage)
            this.archetypeLD.observe(activity, Observer { archetype ->
                if(archetype != null) {
                    archetypeImageView.setImageDrawable(archetype.icon.getDrawable(activity.resources))
                    archetypeImageView.visibility = View.VISIBLE
                    archetypeImageView.contentDescription = activity.getString(R.string.content_desc_archetypeImage, archetype.name)
                } else {
                    archetypeImageView.visibility = View.INVISIBLE
                }
            })

            val abilityImageOneView = view.findViewById<ImageView>(R.id.abilityOneImage)
            val abilityImageTwoView = view.findViewById<ImageView>(R.id.abilityTwoImage)
            val abilityImageThreeView = view.findViewById<ImageView>(R.id.abilityThreeImage)

            val abilityImageFunc = fun(list: List<Ability?>) {
                abilityImageOneView.visibility = View.INVISIBLE
                abilityImageTwoView.visibility = View.INVISIBLE
                abilityImageThreeView.visibility = View.INVISIBLE
                when {
                    list.isNotEmpty() && list[0] != null -> {
                        abilityImageOneView.visibility = View.VISIBLE
                        abilityImageOneView.setImageDrawable(list[0]!!.icon.getDrawable(activity.resources))
                        abilityImageOneView.contentDescription = activity.getString(R.string.content_desc_ability_one, list[0]!!.name)
                    }
                    list.size > 1 && list[1] != null -> {
                        abilityImageTwoView.visibility = View.VISIBLE
                        abilityImageTwoView.setImageDrawable(list[1]!!.icon.getDrawable(activity.resources))
                        abilityImageTwoView.contentDescription = activity.getString(R.string.content_desc_ability_two, list[1]!!.name)
                    }
                    list.size > 2 && list[2] != null -> {
                        abilityImageThreeView.visibility = View.VISIBLE
                        abilityImageThreeView.setImageDrawable(list[2]!!.icon.getDrawable(activity.resources))
                        abilityImageThreeView.contentDescription = activity.getString(R.string.content_desc_ability_three, list[2]!!.name)
                    }
                }
            }

            this.abilitiesLD.observe(activity, Observer { list ->
                abilityImageFunc(list)
            })

            this.showAbilityIconsLD.observe(activity, Observer {
                if(it) {
                    abilityImageFunc(this.abilitiesLD.value?: emptyList())
                } else {
                    abilityImageFunc(emptyList())
                }
            })

            val costOneImageR = view.findViewById<ImageView>(R.id.costOneImageR)
            val costTwoImageR = view.findViewById<ImageView>(R.id.costTwoImageR)
            val costThreeImageR = view.findViewById<ImageView>(R.id.costThreeImageR)
            val costFourImageR = view.findViewById<ImageView>(R.id.costFourImageR)
            val costFiveImageR = view.findViewById<ImageView>(R.id.costFiveImageR)
            val costSixImageR = view.findViewById<ImageView>(R.id.costSixImageR)

            val costTypeOneR = view.findViewById<ImageView>(R.id.costTypeOneR)
            val costTypeTwoR = view.findViewById<ImageView>(R.id.costTypeTwoR)
            val costTypeThreeR = view.findViewById<ImageView>(R.id.costTypeThreeR)
            val costTypeFourR = view.findViewById<ImageView>(R.id.costTypeFourR)
            val costTypeFiveR = view.findViewById<ImageView>(R.id.costTypeFiveR)

            val attackImageR = view.findViewById<ImageView>(R.id.attackR)
            val defenseImageR = view.findViewById<ImageView>(R.id.defenseR)
            val nameR = view.findViewById<TextView>(R.id.nameR)

            val imagesR = arrayOf(
                costOneImageR, costTwoImageR, costThreeImageR,
                costFourImageR, costFiveImageR, costSixImageR,
                costTypeOneR, costTypeTwoR, costTypeThreeR,
                costTypeFourR, costTypeFiveR, attackImageR, defenseImageR, nameR
            )

            val costOneImageL = view.findViewById<ImageView>(R.id.costOneImageL)
            val costTwoImageL = view.findViewById<ImageView>(R.id.costTwoImageL)
            val costThreeImageL = view.findViewById<ImageView>(R.id.costThreeImageL)
            val costFourImageL = view.findViewById<ImageView>(R.id.costFourImageL)
            val costFiveImageL = view.findViewById<ImageView>(R.id.costFiveImageL)
            val costSixImageL = view.findViewById<ImageView>(R.id.costSixImageL)

            val costTypeOneL = view.findViewById<ImageView>(R.id.costTypeOneL)
            val costTypeTwoL = view.findViewById<ImageView>(R.id.costTypeTwoL)
            val costTypeThreeL = view.findViewById<ImageView>(R.id.costTypeThreeL)
            val costTypeFourL = view.findViewById<ImageView>(R.id.costTypeFourL)
            val costTypeFiveL = view.findViewById<ImageView>(R.id.costTypeFiveL)

            val attackImageL = view.findViewById<ImageView>(R.id.attackL)
            val defenseImageL = view.findViewById<ImageView>(R.id.defenseL)
            val nameL = view.findViewById<TextView>(R.id.nameL)

            val imagesL = arrayOf(
                costOneImageL, costTwoImageL, costThreeImageL,
                costFourImageL, costFiveImageL, costSixImageL,
                costTypeOneL, costTypeTwoL, costTypeThreeL,
                costTypeFourL, costTypeFiveL, attackImageL, defenseImageL, nameL
            )

            val hide = fun(images: Array<View>) {
                images.forEach { it.visibility = View.INVISIBLE }
            }

            val hideImages = fun(images: Array<View>, cutoff: Int) {
                images.forEachIndexed { index, view -> view.visibility = if(index < cutoff) View.INVISIBLE else View.VISIBLE }
            }

            val rightOrientationFunc = fun() {
                hideImages(imagesR, imagesR.size - 3)

                this.cost.forEachIndexed { index, cost ->
                    val costPair = when(index) {
                        0 -> Pair(costOneImageR, R.string.content_desc_cost_one_image)
                        1 -> Pair(costTwoImageR, R.string.content_desc_cost_two_image)
                        2 -> Pair(costThreeImageR, R.string.content_desc_cost_three_image)
                        3 -> Pair(costFourImageR, R.string.content_desc_cost_four_image)
                        4 -> Pair(costFiveImageR, R.string.content_desc_cost_five_image)
                        5 -> Pair(costSixImageR, R.string.content_desc_cost_six_image)
                        else -> null
                    }

                    costPair?.also {
                        if(cost == null) {
                            it.first.visibility = View.INVISIBLE
                        } else {
                            it.first.visibility = View.VISIBLE
                            it.first.setImageDrawable(cost.icon.getDrawable(activity.resources))
                            it.first.contentDescription = activity.getString(it.second, when(cost) {
                                is Cost.GroupCost -> activity.getString(cost.group.groupName)
                                is Cost.OpenCost -> activity.getString(R.string.group_none_name)
                            })
                        }
                    }
                }
                this.costType.forEachIndexed { index, cost ->
                    val costImagePair = when(index){
                        0 -> Pair(costTypeOneR, R.string.content_desc_payment_one_image)
                        1 -> Pair(costTypeTwoR, R.string.content_desc_payment_two_image)
                        2 -> Pair(costTypeThreeR, R.string.content_desc_payment_three_image)
                        3 -> Pair(costTypeFourR, R.string.content_desc_payment_four_image)
                        4 -> Pair(costTypeFiveR, R.string.content_desc_payment_five_image)
                        else -> null
                    }

                    costImagePair?.also {
                        if(cost == null) {
                            it.first.visibility = View.INVISIBLE
                        } else {
                            it.first.visibility = View.VISIBLE
                            it.first.setImageDrawable(cost.costIcon.getDrawable(activity.resources))
                            it.first.contentDescription = activity.getString(it.second, when(cost) {
                                is Cost.GroupCost -> activity.getString(cost.group.groupName)
                                is Cost.OpenCost -> activity.getString(R.string.group_none_name)
                            })
                        }
                    }
                }
                attackImageR.visibility = View.VISIBLE
                attackImageR.setImageDrawable(AttributeImage.forInt(this.attack).attackImage.getDrawable(activity.resources))
                attackImageR.contentDescription = activity.getString(R.string.content_desc_attack_image, this.attack)

                defenseImageR.visibility = View.VISIBLE
                defenseImageR.setImageDrawable(AttributeImage.forInt(this.defense).defenseImage.getDrawable(activity.resources))
                defenseImageR.contentDescription = activity.getString(R.string.content_desc_defense_image, this.defense)

                nameR.visibility = View.VISIBLE
                nameR.text = this.cardName
            }

            val leftOrientationFunc = fun() {
                hideImages(imagesL, imagesL.size - 3)
                this.cost.forEachIndexed { index, cost ->
                    val costPair = when(index) {
                        0 -> Pair(costOneImageL, R.string.content_desc_cost_one_image)
                        1 -> Pair(costTwoImageL, R.string.content_desc_cost_two_image)
                        2 -> Pair(costThreeImageL, R.string.content_desc_cost_three_image)
                        3 -> Pair(costFourImageL, R.string.content_desc_cost_four_image)
                        4 -> Pair(costFiveImageL, R.string.content_desc_cost_five_image)
                        5 -> Pair(costSixImageL, R.string.content_desc_cost_six_image)
                        else -> null
                    }

                    costPair?.also {
                        if(cost == null) {
                            it.first.visibility = View.INVISIBLE
                        } else {
                            it.first.visibility = View.VISIBLE
                            it.first.setImageDrawable(cost.icon.getDrawable(activity.resources))
                            it.first.contentDescription = activity.getString(it.second, when(cost) {
                                is Cost.GroupCost -> activity.getString(cost.group.groupName)
                                is Cost.OpenCost -> activity.getString(R.string.group_none_name)
                            })
                        }
                    }
                }
                this.costType.forEachIndexed { index, cost ->
                    val costImagePair = when(index){
                        0 -> Pair(costTypeOneL, R.string.content_desc_payment_one_image)
                        1 -> Pair(costTypeTwoL, R.string.content_desc_payment_two_image)
                        2 -> Pair(costTypeThreeL, R.string.content_desc_payment_three_image)
                        3 -> Pair(costTypeFourL, R.string.content_desc_payment_four_image)
                        4 -> Pair(costTypeFiveL, R.string.content_desc_payment_five_image)
                        else -> null
                    }

                    costImagePair?.also {
                        if(cost == null) {
                            it.first.visibility = View.INVISIBLE
                        } else {
                            it.first.visibility = View.VISIBLE
                            it.first.setImageDrawable(cost.costIcon.getDrawable(activity.resources))
                            it.first.contentDescription = activity.getString(it.second, when(cost) {
                                is Cost.GroupCost -> activity.getString(cost.group.groupName)
                                is Cost.OpenCost -> activity.getString(R.string.group_none_name)
                            })
                        }
                    }
                }
                attackImageL.visibility = View.VISIBLE
                attackImageL.setImageDrawable(AttributeImage.forInt(this.attack).attackImage.getDrawable(activity.resources))

                defenseImageL.visibility = View.VISIBLE
                defenseImageL.setImageDrawable(AttributeImage.forInt(this.defense).defenseImage.getDrawable(activity.resources))

                nameL.visibility = View.VISIBLE
                nameL.text = this.cardName
            }

            this.cardNameLD.observe(activity, Observer {
                nameL.text = this.cardName
                nameR.text = this.cardName
            })

            this.cardNameStyleLD.observe(activity, Observer {
                nameL.setTextAppearance(it)
                nameR.setTextAppearance(it)
            })

            this.showRightCostBarLD.observe(activity, Observer {
                if(it) {
                    hide(imagesL)
                    rightOrientationFunc()
                } else {
                    hide(imagesR)
                    leftOrientationFunc()
                }
            })

            this.costLD.observe(activity, Observer {
                if(this.showRightCostBar) {
                    hide(imagesL)
                    rightOrientationFunc()
                } else {
                    hide(imagesR)
                    leftOrientationFunc()
                }
            })
            this.costTypeLD.observe(activity, Observer {
                if(this.showRightCostBar) {
                    hide(imagesL)
                    rightOrientationFunc()
                } else {
                    hide(imagesR)
                    leftOrientationFunc()
                }
            })
        }

        this.card = this.card
    }

    fun unWireView(activity: LifecycleOwner) {
        listOf(
            this.abilitiesLD,
            this.cardBackgroundLD,
            this.cardNameLD,
            this.cardTextStyleLD,
            this.cardTextBackgroundLD,
            this.cardImageBackgroundLD,
            this.cardImageLD,
            this.cardNameStyleLD,
            this.cardTextLD,
            this.costLD,
            this.costTypeLD,
            this.archetypeLD,
            this.attackImageLD,
            this.defenseImageLD,
            this.factionLD,
            this.flavorTextLD,
            this.flavorTextStyleLD,
            this.flavorTextBackgroundLD,
            this.layoutLD,
            this.showAbilityIconsLD,
            this.showRightCostBarLD,
            this.showLeftCostBarLD
        ).forEach { it.removeObservers(activity) }
    }

    companion object {
        val nullIconImage = Image(R.drawable.ic_invalid)
        val nullBackground = Image(R.drawable.background_none)
    }
}