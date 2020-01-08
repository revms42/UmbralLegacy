package org.ajar.umbrallegacy.model

import android.content.Context
import org.ajar.umbrallegacy.R

interface AbilityDefinition {

    val abilityName: Int
    val description: Int

    fun name(context: Context): String {
        return context.getString(abilityName)
    }

    fun describe(context: Context): String {
        return context.getString(description)
    }

    companion object {
        var initialized = false

        internal val _allAbilities = ArrayList<AbilityDefinition>()
        val allAbilities: List<AbilityDefinition>
            get() {
                if(!initialized) {
                    FactionAbility.register()
                }
                return _allAbilities
            }
    }
}

enum class FactionAbility(override val abilityName: Int, override val description: Int) : AbilityDefinition {
    VAMPIRE_ABILITY(R.string.ability_vampire_name, R.string.ability_vampire_description),
    NOSFERATU_ABILITY(R.string.ability_nosferatu_name, R.string.ability_nosferatu_description),
    WIGHT_ABILITY(R.string.ability_wight_name, R.string.ability_wight_description),
    ZOMBIE_ABILITY(R.string.ability_zombie_name, R.string.ability_zombie_description),

    LYCANTHROPE_ABILITY(R.string.ability_lycanthrope_name, R.string.ability_lycanthrope_description),
    ANIMATED_ABILITY(R.string.ability_animated_name, R.string.ability_animated_description),
    TOTEMIC_SPIRIT_ABILITY(R.string.ability_totemic_spirit_name, R.string.ability_totemic_spirit_description),
    WITCH_BOUND_ABILITY(R.string.ability_witch_bound_name, R.string.ability_witch_bound_description),

    WARLOCK_ABILITY(R.string.ability_warlock_name, R.string.ability_warlock_description),
    ELEMENTAL_ABILITY(R.string.ability_elemental_name, R.string.ability_elemental_description),
    BOUND_DEMON_ABILITY(R.string.ability_bound_demon_name, R.string.ability_bound_demon_description),
    MAGICAL_CONSTRUCT_ABILITY(R.string.ability_magical_construct_name, R.string.ability_magical_construct_description),

    HORNED_ONE_ABILITY(R.string.ability_horned_one_name, R.string.ability_horned_one_description),
    WISH_GRANTER_ABILITY(R.string.ability_wish_granter_name, R.string.ability_wish_granter_description),
    UNSEELIE_ABILITY(R.string.ability_unseelie_name, R.string.ability_unseelie_description),
    TROLL_ABILITY(R.string.ability_troll_name, R.string.ability_troll_description),

    GREAT_OLD_ONE_ABILITY(R.string.ability_great_old_one_name, R.string.ability_great_old_one_description),
    ANCIENT_RACE_ABILITY(R.string.ability_ancient_race_name, R.string.ability_ancient_race_description),
    CULTIST_ABILITY(R.string.ability_cultist_name, R.string.ability_cultist_description),
    YELLOW_MASK_ABILITY(R.string.ability_yellow_mask_name, R.string.ability_yellow_mask_description),

    ARCHITECT_ABILITY(R.string.ability_architect_name, R.string.ability_architect_description),
    DOPPELGANGER_ABILITY(R.string.ability_doppelganger_name, R.string.ability_doppelganger_description),
    MARKED_ABILITY(R.string.ability_marked_name, R.string.ability_marked_description),
    HELLSPAWN_ABILITY(R.string.ability_hellspawn_name, R.string.ability_hellspawn_description);

    companion object {
        fun register() {
            AbilityDefinition._allAbilities.addAll(values())
        }
    }
}