package org.ajar.umbrallegacy.content

import androidx.room.TypeConverter
import org.ajar.umbrallegacy.model.PrincipleAbilityType

class ContentTypeConverter {
    @TypeConverter
    fun fromInteger(ordinal: Int?) : PrincipleAbilityType? {
        return if(ordinal in 0..PrincipleAbilityType.values().size) {
            PrincipleAbilityType.values()[ordinal!!]
        } else {
            null
        }
    }

    @TypeConverter
    fun toInteger(type: PrincipleAbilityType?) : Int? {
        return type?.ordinal
    }
}