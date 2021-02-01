package org.ajar.umbrallegacy.content

import androidx.room.TypeConverter
import org.ajar.umbrallegacy.model.CardLayout
import org.ajar.umbrallegacy.model.Image
import org.ajar.umbrallegacy.model.PrincipleAbilityType

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
}