package org.ajar.umbrallegacy.content

import androidx.room.TypeConverter
import org.ajar.umbrallegacy.model.*

class ContentTypeConverter {
    @TypeConverter
    fun fromAbilityTypeToInt(ordinal: Int?) : PrincipleAbilityType? {
        return if(ordinal in 0..PrincipleAbilityType.values().size) {
            PrincipleAbilityType.values()[ordinal!!]
        } else {
            null
        }
    }

    @TypeConverter
    fun toIntFromAbilityType(type: PrincipleAbilityType?) : Int? {
        return type?.ordinal
    }

    @TypeConverter
    fun fromImageToString(image: Image?) : String? {
        return image?.toString()
    }

    @TypeConverter
    fun fromStringToImage(str: String?) : Image {
        return Image.fromString(str?:"")
    }

    @TypeConverter
    fun fromCardLayoutToInt(layout: CardLayout?) : Int? {
        return layout?.ordinal
    }

    @TypeConverter
    fun fromIntToCardLayout(res: Int?) : CardLayout {
        return CardLayout.values()[res?:0]
    }

    @TypeConverter
    fun fromCostToString(cost: Cost?) : String? {
        return cost.toString()
    }

    @TypeConverter
    fun fromStringToCost(str: String?) : Cost? {
        return str?.let { Cost.fromString(str) }
    }

    @TypeConverter
    fun fromListCostToString(cost: List<Cost>?) : String? {
        return cost?.joinToString()
    }

    @TypeConverter
    fun fromStringToListCost(str: String?) : List<Cost>? {
        return str?.let { it.split(",").mapNotNull { cost -> fromStringToCost(cost) } }
    }

    @TypeConverter
    fun fromFactionToInt(faction: Faction?) : Int? {
        return faction?.ordinal
    }

    @TypeConverter
    fun fromIntToFaction(res: Int?) : Faction? {
        return res?.let { Faction.values()[it] }
    }

    @TypeConverter
    fun fromIntListToString(list: List<Int>?) : String? {
        return list?.joinToString()
    }

    @TypeConverter
    fun fromStringToIntList(str: String?) : List<Int?>? {
        return str?.let { string ->
            string.split(",").mapNotNull { res -> if(res.isBlank()) null else res.toInt() }
        }
    }
}