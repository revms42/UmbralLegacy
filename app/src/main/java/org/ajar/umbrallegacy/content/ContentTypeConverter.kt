package org.ajar.umbrallegacy.content

import androidx.room.TypeConverter
import org.ajar.umbrallegacy.model.AbilityType

class ContentTypeConverter {
    @TypeConverter
    fun fromInteger(ordinal: Int?) : AbilityType? {
        return if(ordinal in 0..AbilityType.values().size) {
            AbilityType.values()[ordinal!!]
        } else {
            null
        }
    }

    @TypeConverter
    fun toInteger(type: AbilityType?) : Int? {
        return type?.ordinal
    }
}