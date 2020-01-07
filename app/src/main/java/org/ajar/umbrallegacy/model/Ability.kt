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
    VampireFactionAbility(R.string.ability_vampire_name, R.string.ability_vampire_description),
    NosferatuFactionAbility(R.string.ability_nosferatu_name, R.string.ability_nosferatu_description),
    WightFactionAbility(R.string.ability_wight_name, R.string.ability_wight_description),
    ZombieFactionAbility(R.string.ability_zombie_name, R.string.ability_zombie_description),

    LycanthropeFactionAbility(R.string.ability_lycanthrope_name, R.string.ability_lycanthrope_description),
    AnimatedFactionAbility(R.string.ability_animated_name, R.string.ability_animated_description),
    TotemicSpiritFactionAbility(R.string.ability_totemic_spirit_name, R.string.ability_totemic_spirit_description),
    WitchBoundFactionAbility(R.string.ability_witch_bound_name, R.string.ability_witch_bound_description),

    WarlockFactionAbility(R.string.ability_warlock_name, R.string.ability_warlock_description),
    ElementalFactionAbility(R.string.ability_elemental_name, R.string.ability_elemental_description),
    BoundDemonFactionAbility(R.string.ability_bound_demon_name, R.string.ability_bound_demon_description),
    MagicalConstructFactionAbility(R.string.ability_magical_construct_name, R.string.ability_magical_construct_description),

    HornedOneFactionAbility(R.string.ability_horned_one_name, R.string.ability_horned_one_description),
    WishGranterFactionAbility(R.string.ability_wish_granter_name, R.string.ability_wish_granter_description),
    UnseelieFactionAbility(R.string.ability_unseelie_name, R.string.ability_unseelie_description),
    TrollFactionAbility(R.string.ability_troll_name, R.string.ability_troll_description),

    GreatOldOneFactionAbility(R.string.ability_great_old_one_name, R.string.ability_great_old_one_description),
    AncientRaceFactionAbility(R.string.ability_ancient_race_name, R.string.ability_ancient_race_description),
    CultistFactionAbility(R.string.ability_cultist_name, R.string.ability_cultist_description),
    YellowMaskFactionAbility(R.string.ability_yellow_mask_name, R.string.ability_yellow_mask_description),

    ArchitectFactionAbility(R.string.ability_architect_name, R.string.ability_architect_description),
    DoppelgangerFactionAbility(R.string.ability_doppelganger_name, R.string.ability_doppelganger_description),
    MarkedFactionAbility(R.string.ability_marked_name, R.string.ability_marked_description),
    HellspawnFactionAbility(R.string.ability_hellspawn_name, R.string.ability_hellspawn_description);

    companion object {
        fun register() {
            AbilityDefinition._allAbilities.addAll(values())
        }
    }
}