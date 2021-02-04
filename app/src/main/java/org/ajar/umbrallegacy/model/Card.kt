package org.ajar.umbrallegacy.model

import androidx.annotation.LayoutRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.ajar.umbrallegacy.R

interface CardDefinition {
    var id: Int
    var name: String
    var faction: Faction?
    var attack: Int
    var defense: Int
    var cost: List<Cost?>
    var costType: List<Cost?>
    var abilities: List<Int?>

    var cardImage: Image?
    var cardText: String?
    var flavorText: String?
    var layout: CardLayout
    var showLeftCostBar: Boolean
    var showRightCostBar: Boolean
    var showAbilityIcons: Boolean
}

enum class CardLayout(@LayoutRes val layoutRes: Int? = null) {
    IMAGE_TEXT_HORIZONTAL_SPLIT(R.layout.card_horizontal_split),
    IMAGE_TEXT_VERTICAL_SPLIT,
    IMAGE_TEXT_DIAGONAL_SPLIT,
    TEXT_OVER_IMAGE_TRANSPARENT,
    TEXT_OVER_IMAGE_BACKGROUND
}

@Entity(tableName = Card.TABLE_NAME)
class Card(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(index = true, name = COLUMN_ID)  override var id: Int = 0,
    @ColumnInfo(name = COLUMN_NAME) override var name: String = "",
    @ColumnInfo(name = COLUMN_FACTION) override var faction: Faction? = null,
    @ColumnInfo(name = COLUMN_ATTACK) override var attack: Int = 0,
    @ColumnInfo(name = COLUMN_DEFENSE) override var defense: Int = 0,
    @ColumnInfo(name = COLUMN_COST) override var cost: List<Cost?> = emptyList(),
    @ColumnInfo(name = COLUMN_COST_TYPE) override var costType: List<Cost?> = emptyList(),
    @ColumnInfo(name = COLUMN_ABILITIES) override var abilities: List<Int?> = emptyList(),
    @ColumnInfo(name = COLUMN_IMAGE) override var cardImage: Image? = null,
    @ColumnInfo(name = COLUMN_TEXT) override var cardText: String? = null,
    @ColumnInfo(name = COLUMN_FLAVOR) override var flavorText: String? =  null,
    @ColumnInfo(name = COLUMN_LAYOUT) override var layout: CardLayout = CardLayout.IMAGE_TEXT_HORIZONTAL_SPLIT,
    @ColumnInfo(name = COLUMN_SHOW_LEFT) override var showLeftCostBar: Boolean = false,
    @ColumnInfo(name = COLUMN_SHOW_RIGHT) override var showRightCostBar: Boolean = false,
    @ColumnInfo(name = COLUMN_SHOW_ABILITY) override var showAbilityIcons: Boolean = false
) : CardDefinition {
    companion object {
        const val TABLE_NAME = "cards"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_FACTION = "fact"
        const val COLUMN_ATTACK = "atk"
        const val COLUMN_DEFENSE = "def"
        const val COLUMN_COST = "cost"
        const val COLUMN_COST_TYPE = "cost_type"
        const val COLUMN_ABILITIES = "abl"
        const val COLUMN_IMAGE = "img"
        const val COLUMN_TEXT = "txt"
        const val COLUMN_FLAVOR = "flv"
        const val COLUMN_LAYOUT = "layout"
        const val COLUMN_SHOW_LEFT = "left"
        const val COLUMN_SHOW_RIGHT = "right"
        const val COLUMN_SHOW_ABILITY = "show_abl"
    }
}