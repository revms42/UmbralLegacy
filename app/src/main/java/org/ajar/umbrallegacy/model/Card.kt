package org.ajar.umbrallegacy.model

import androidx.annotation.LayoutRes
import org.ajar.umbrallegacy.R

interface Card {
    val id: Int
    var name: String
    var faction: Faction
    var attack: Int
    var defense: Int
    var price: Int
    var bloodPrice: Int
    val abilities: List<Int>

    var cardImage: Image?
    var cardText: String?
    var flavorText: String?
    var layout: CardLayout
    var showLeftCostBar: Boolean
    var showRightCostBar: Boolean
    var showAbilityIcons: Boolean
}

enum class CardLayout(@LayoutRes layout: Int? = null) {
    IMAGE_TEXT_HORIZONTAL_SPLIT(R.layout.card_horizontal_split),
    IMAGE_TEXT_VERTICAL_SPLIT,
    IMAGE_TEXT_DIAGONAL_SPLIT,
    TEXT_OVER_IMAGE_TRANSPARENT,
    TEXT_OVER_IMAGE_BACKGROUND
}