package org.ajar.umbrallegacy.ui.card

import androidx.annotation.DrawableRes
import org.ajar.umbrallegacy.R
import org.ajar.umbrallegacy.model.Image

enum class AttributeImage(@DrawableRes attackRes: Int, @DrawableRes defenseRes: Int){
    ZERO(R.drawable.ic_attack_zero, R.drawable.ic_defense_zero);

    val attackImage: Image = Image(attackRes)
    val defenseImage: Image = Image(defenseRes)

    companion object {
        fun forInt(res: Int): AttributeImage {
            return when {
                res >= 0 && res < values().size -> values()[res]
                else -> values()[0]
            }
        }
    }
}