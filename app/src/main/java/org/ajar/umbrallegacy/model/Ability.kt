package org.ajar.umbrallegacy.model

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.ajar.umbrallegacy.R

enum class AbilityType(private val term: Int, var icon: Int = -1) {
    FACTION(R.string.term_faction_ability) {
        override val collection: Array<out AbilityDefinition>
            get() = FactionAbility.values()
    },
    POSITIVE(R.string.term_positive_ability) {
        override val collection: Array<out AbilityDefinition>
            get() = PositiveAbility.values()

    },
    NEGATIVE(R.string.term_negative_ability) {
        override val collection: Array<out AbilityDefinition>
            get() = NegativeAbility.values()
    },
    ARCHETYPE(R.string.term_archetype_ability) {
        override val collection: Array<out AbilityDefinition>
            get() = ArchetypeAbilities.values()
    },
    COMBAT(R.string.term_combat_ability) {
        override val collection: Array<out AbilityDefinition>
            get() = CombatAbilities.values()
    };

    abstract val collection: Array<out AbilityDefinition>

    fun displayName(context: Context): String {
        return context.getString(term)
    }
}

interface AbilityDefinition {

    val abilityName: Int
    val description: Int
    val type: AbilityType
    var cost: Int

    fun name(context: Context): String {
        return context.getString(abilityName)
    }

    fun describe(context: Context): String {
        return context.getString(description)
    }

    fun type(context: Context): String {
        return type.displayName(context)
    }

    companion object {
        private var initialized = false

        private val _abilityMap = HashMap<Int, AbilityDefinition>()
        private val abilityMap: HashMap<Int, AbilityDefinition>
            get() {
                if(!initialized) {
                    FactionAbility.values().forEach { _abilityMap[it.abilityName] = it }
                    PositiveAbility.values().forEach { _abilityMap[it.abilityName] = it }
                    NegativeAbility.values().forEach { _abilityMap[it.abilityName] = it }
                    ArchetypeAbilities.values().forEach { _abilityMap[it.abilityName] = it }
                    CombatAbilities.values().forEach { _abilityMap[it.abilityName] = it }
                    initialized = true
                }
                return _abilityMap
            }

        val allAbilities: Collection<AbilityDefinition>
            get() {
                return abilityMap.values
            }

        operator fun get(key: Int) : AbilityDefinition? {
            return abilityMap[key]
        }
    }
}

@Entity(tableName = Ability.TABLE_NAME)
data class Ability(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(index = true, name = COLUMN_ID) var id: Long,
    @ColumnInfo(name = COLUMN_NAME) var name: String,
    @ColumnInfo(name = COLUMN_DESC) var description: String,
    @ColumnInfo(name = COLUMN_TYPE) var type: AbilityType,
    @ColumnInfo(name = COLUMN_COST) var cost: Int
) {

    companion object {
        const val TABLE_NAME = "ability"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DESC = "description"
        const val COLUMN_TYPE = "type"
        const val COLUMN_COST = "cost"

        fun create(definition: AbilityDefinition, context: Context) : Ability {
            return Ability(0, definition.name(context), definition.describe(context), definition.type, 1)
        }
    }
}

enum class FactionAbility(override val abilityName: Int, override val description: Int, override var cost: Int = 1) : AbilityDefinition {
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

    override val type = AbilityType.FACTION
}

enum class PositiveAbility (override val abilityName: Int, override val description: Int, override var cost: Int = 1) : AbilityDefinition {

    BURN(R.string.ability_burn_name, R.string.ability_burn_description),
    SACRIFICE(R.string.ability_sacrifice_name, R.string.ability_sacrifice_description),
    PARALYZE(R.string.ability_paralyze_name, R.string.ability_paralyze_description),
    RELENTLESS(R.string.ability_relentless_name, R.string.ability_relentless_description),
    DECAYING(R.string.ability_decaying_name, R.string.ability_decaying_description),
    PROTECTION_GREATER(R.string.ability_protection_greater_name, R.string.ability_protection_greater_description),
    PROTECTION_LESSER(R.string.ability_protection_lesser_name, R.string.ability_protection_lesser_description),
    HYPNOSIS(R.string.ability_hypnosis_name, R.string.ability_hypnosis_description),
    RITUAL(R.string.ability_ritual_name, R.string.ability_ritual_description),
    LORD(R.string.ability_lord_name, R.string.ability_lord_description),
    VINDICTIVE(R.string.ability_vindictive_name, R.string.ability_vindictive_description),
    CURSE(R.string.ability_curse_name, R.string.ability_curse_description),
    WITHER(R.string.ability_wither_name, R.string.ability_wither_description),
    POISON(R.string.ability_poison_name, R.string.ability_poison_description),
    FATE(R.string.ability_fate_name, R.string.ability_fate_description),
    HERESY(R.string.ability_heresy_name, R.string.ability_heresy_description),
    TAXATION(R.string.ability_taxation_name, R.string.ability_taxation_description),
    NECROMANCY(R.string.ability_necromancy_name, R.string.ability_necromancy_description),
    DARK_REVELATION(R.string.ability_dark_revelation_name, R.string.ability_dark_revelation_description),
    AMNESIA(R.string.ability_amnesia_name, R.string.ability_amnesia_description),
    FONT(R.string.ability_font_name, R.string.ability_font_description),
    PRIMAL_NATURE(R.string.ability_primal_nature_name, R.string.ability_primal_nature_description),
    ASSASSIN(R.string.ability_assassin_name, R.string.ability_assassin_description),
    SEANCE(R.string.ability_seance_name, R.string.ability_seance_description),
    CHAMPION(R.string.ability_champion_name, R.string.ability_champion_description),
    UNHOLY_TRANSFORMATION(R.string.ability_unholy_transformation_name, R.string.ability_unholy_transformation_description),
    HEX(R.string.ability_hex_name, R.string.ability_hex_description),
    GRAVE_ROBBER(R.string.ability_grave_robber_name, R.string.ability_grave_robber_description),
    DEMONIC_FIDDLER(R.string.ability_demonic_fiddler_name, R.string.ability_demonic_fiddler_description),
    FRIGHTENING_ASSAULT(R.string.ability_frightening_assault_name, R.string.ability_frightening_assault_description);

    override val type = AbilityType.POSITIVE
}

enum class NegativeAbility(override val abilityName: Int, override val description: Int, override var cost: Int = 1) : AbilityDefinition {
    FRAGILE(R.string.ability_fragile_name, R.string.ability_fragile_description),
    HUNGRY(R.string.ability_hungry_name, R.string.ability_hungry_description),
    BLOOD_PRICE(R.string.ability_blood_price_name, R.string.ability_blood_price_description),
    DARK_COST(R.string.ability_dark_cost_name, R.string.ability_dark_cost_description),
    VENGEFUL(R.string.ability_vengeful_name, R.string.ability_vengeful_description),
    COWARDLY(R.string.ability_cowardly_name, R.string.ability_cowardly_description),
    BRASH(R.string.ability_brash_name, R.string.ability_brash_description),
    HOLLOW(R.string.ability_hollow_name, R.string.ability_hollow_description),
    ANCIENT(R.string.ability_ancient_name, R.string.ability_ancient_description),
    UNIQUE(R.string.ability_unique_name, R.string.ability_unique_description),
    ETHERIAL(R.string.ability_etherial_name, R.string.ability_etherial_description),
    GHOUL(R.string.ability_ghoul_name, R.string.ability_ghoul_description),
    SYCOPHANT(R.string.ability_sycophant_name, R.string.ability_sycophant_description),
    SLOTH(R.string.ability_sloth_name, R.string.ability_sloth_description),
    DEATHS_GIFT(R.string.ability_deaths_gift_name, R.string.ability_deaths_gift_description);

    override val type = AbilityType.NEGATIVE
}

enum class ArchetypeAbilities(override val abilityName: Int, override val description: Int, override var cost: Int = 1) : AbilityDefinition {
    ARBITER(R.string.ability_arbiter_name, R.string.ability_arbiter_description),
    REAPER(R.string.ability_reaper_name, R.string.ability_reaper_description),
    EXECUTIONER(R.string.ability_executioner_name, R.string.ability_executioner_description),
    LUNATIC_PIPER(R.string.ability_lunatic_piper_name, R.string.ability_lunatic_piper_description),
    LICH(R.string.ability_lich_name, R.string.ability_lich_description),
    INQUISITOR(R.string.ability_inquisitor_name, R.string.ability_inquisitor_description);

    override val type = AbilityType.ARCHETYPE
}

enum class CombatAbilities(override val abilityName: Int, override val description: Int, override var cost: Int = 1) : AbilityDefinition {
    PHALANX(R.string.ability_phalanx_name, R.string.ability_phalanx_description),
    ARTILLERY(R.string.ability_artillery_name, R.string.ability_artillery_description),
    OVERRUN(R.string.ability_overrun_name, R.string.ability_overrun_description),
    FLANKING(R.string.ability_flanking_name, R.string.ability_flanking_description),
    WEDGE(R.string.ability_wedge_name, R.string.ability_wedge_description),
    REACH(R.string.ability_reach_name, R.string.ability_reach_description);

    override val type = AbilityType.COMBAT
}