package org.ajar.umbrallegacy.model

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.ajar.umbrallegacy.R

interface AbilityType {
    var icon: Int
    var primaryColor: Int
    var secondaryColor: Int
    val collection: Array<out AbilityDefinition>
    fun displayName(context: Context) : String
}

enum class PrincipleAbilityType(private val term: Int, override var icon: Int = -1, override var primaryColor: Int = -1, override var secondaryColor: Int = -1) : AbilityType{
    FACTION(R.string.term_faction_ability, R.drawable.ic_faction_generic, R.color.abilityTypeFactionPrimary, R.color.abilityTypeFactionSecondary) {
        override val collection: Array<out AbilityDefinition>
            get() = FactionAbility.values()
    },
    POSITIVE(R.string.term_positive_ability, R.drawable.ic_beneficial, R.color.abilityTypeBeneficialPrimary, R.color.abilityTypeBeneficialSecondary) {
        override val collection: Array<out AbilityDefinition>
            get() = PositiveAbility.values()

    },
    NEGATIVE(R.string.term_negative_ability, R.drawable.ic_detrimental, R.color.abilityTypeDetrimentalPrimary, R.color.abilityTypeDetrimentalSecondary) {
        override val collection: Array<out AbilityDefinition>
            get() = NegativeAbility.values()
    },
    ARCHETYPE(R.string.term_archetype_ability, R.drawable.ic_archetype, R.color.abilityTypeArchetypePrimary, R.color.abilityTypeArchetypeSecondary) {
        override val collection: Array<out AbilityDefinition>
            get() = ArchetypeAbilities.values()
    },
    COMBAT(R.string.term_combat_ability, R.drawable.ic_combat, R.color.abilityTypeCombatPrimary, R.color.abilityTypeCombatSecondary) {
        override val collection: Array<out AbilityDefinition>
            get() = CombatAbilities.values()
    };

    override fun displayName(context: Context): String {
        return context.getString(term)
    }

    companion object {
        fun fromDisplayName(context: Context, name: String): PrincipleAbilityType? {
            return values().firstOrNull { it.displayName(context) == name }
        }
    }
}

interface AbilityDefinition {

    val abilityName: Int
    val description: Int
    val type: PrincipleAbilityType
    val icon: Int
    val primaryColor: Int
    val secondaryColor: Int
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

        fun getFromName(name: String, context: Context) : AbilityDefinition? {
            return abilityMap.values.firstOrNull { context.getString(it.abilityName) == name }
        }
    }
}

@Entity(tableName = Ability.TABLE_NAME)
data class Ability (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(index = true, name = COLUMN_ID) var id: Long,
    @ColumnInfo(name = COLUMN_NAME) var name: String,
    @ColumnInfo(name = COLUMN_DESC) var description: String,
    @ColumnInfo(name = COLUMN_TYPE) var type: PrincipleAbilityType,
    @ColumnInfo(name = COLUMN_ICON) @DrawableRes var icon: Int? = null,
    @ColumnInfo(name = COLUMN_COST) var cost: Int = 1
) {

    companion object {
        const val TABLE_NAME = "ability"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DESC = "description"
        const val COLUMN_ICON = "icon"
        const val COLUMN_TYPE = "type"
        const val COLUMN_COST = "cost"

        fun create(definition: AbilityDefinition, context: Context) : Ability {
            return Ability(0, definition.name(context), definition.describe(context), definition.type)
        }
    }
}

enum class FactionAbility(override val abilityName: Int, override val description: Int,
                          @DrawableRes override val icon: Int = -1, @ColorRes override val primaryColor: Int = -1,
                          @ColorRes override val secondaryColor: Int = -1, override var cost: Int = 1) : AbilityDefinition {
    VAMPIRE_ABILITY(R.string.ability_vampire_name, R.string.ability_vampire_description, R.drawable.ic_group_stillblood, R.color.groupStillbloodPrimary, R.color.groupStillbloodSecondary, R.integer.vampire_cost),
    NOSFERATU_ABILITY(R.string.ability_nosferatu_name, R.string.ability_nosferatu_description, R.drawable.ic_group_stillblood, R.color.groupStillbloodPrimary, R.color.groupStillbloodSecondary, R.integer.nosferatu_cost),
    WIGHT_ABILITY(R.string.ability_wight_name, R.string.ability_wight_description, R.drawable.ic_group_stillblood, R.color.groupStillbloodPrimary, R.color.groupStillbloodSecondary, R.integer.wight_cost),
    ZOMBIE_ABILITY(R.string.ability_zombie_name, R.string.ability_zombie_description, R.drawable.ic_group_stillblood, R.color.groupStillbloodPrimary, R.color.groupStillbloodSecondary, R.integer.zombie_cost),

    LYCANTHROPE_ABILITY(R.string.ability_lycanthrope_name, R.string.ability_lycanthrope_description, R.drawable.ic_group_cursed, R.color.groupCursedPrimary, R.color.groupCursedSecondary, R.integer.lychanthrope_cost),
    ANIMATED_ABILITY(R.string.ability_animated_name, R.string.ability_animated_description, R.drawable.ic_group_cursed, R.color.groupCursedPrimary, R.color.groupCursedSecondary, R.integer.animated_cost),
    TOTEMIC_SPIRIT_ABILITY(R.string.ability_totemic_spirit_name, R.string.ability_totemic_spirit_description, R.drawable.ic_group_cursed, R.color.groupCursedPrimary, R.color.groupCursedSecondary, R.integer.totemic_spirit_cost),
    WITCH_BOUND_ABILITY(R.string.ability_witch_bound_name, R.string.ability_witch_bound_description, R.drawable.ic_group_cursed, R.color.groupCursedPrimary, R.color.groupCursedSecondary, R.integer.witch_bound_cost),

    WARLOCK_ABILITY(R.string.ability_warlock_name, R.string.ability_warlock_description, R.drawable.ic_group_piercers, R.color.groupPiercersPrimary, R.color.groupPiercersSecondary, R.integer.warlock_cost),
    ELEMENTAL_ABILITY(R.string.ability_elemental_name, R.string.ability_elemental_description, R.drawable.ic_group_piercers, R.color.groupPiercersPrimary, R.color.groupPiercersSecondary, R.integer.elemental_cost),
    BOUND_DEMON_ABILITY(R.string.ability_bound_demon_name, R.string.ability_bound_demon_description, R.drawable.ic_group_piercers, R.color.groupPiercersPrimary, R.color.groupPiercersSecondary, R.integer.bound_demon_cost),
    MAGICAL_CONSTRUCT_ABILITY(R.string.ability_magical_construct_name, R.string.ability_magical_construct_description, R.drawable.ic_group_piercers, R.color.groupPiercersPrimary, R.color.groupPiercersSecondary, R.integer.magical_construct_cost),

    HORNED_ONE_ABILITY(R.string.ability_horned_one_name, R.string.ability_horned_one_description, R.drawable.ic_group_fae, R.color.groupFaePrimary, R.color.groupFaeSecondary, R.integer.horned_one_cost),
    WISH_GRANTER_ABILITY(R.string.ability_wish_granter_name, R.string.ability_wish_granter_description, R.drawable.ic_group_fae, R.color.groupFaePrimary, R.color.groupFaeSecondary, R.integer.wish_granter_cost),
    UNSEELIE_ABILITY(R.string.ability_unseelie_name, R.string.ability_unseelie_description, R.drawable.ic_group_fae, R.color.groupFaePrimary, R.color.groupFaeSecondary, R.integer.unseelie_cost),
    TROLL_ABILITY(R.string.ability_troll_name, R.string.ability_troll_description, R.drawable.ic_group_fae, R.color.groupFaePrimary, R.color.groupFaeSecondary, R.integer.troll_cost),

    GREAT_OLD_ONE_ABILITY(R.string.ability_great_old_one_name, R.string.ability_great_old_one_description, R.drawable.ic_group_old_ones, R.color.groupOldOnesPrimary, R.color.groupOldOnesSecondary, R.integer.great_old_one_cost),
    ANCIENT_RACE_ABILITY(R.string.ability_ancient_race_name, R.string.ability_ancient_race_description, R.drawable.ic_group_old_ones, R.color.groupOldOnesPrimary, R.color.groupOldOnesSecondary, R.integer.ancient_race_cost),
    CULTIST_ABILITY(R.string.ability_cultist_name, R.string.ability_cultist_description, R.drawable.ic_group_old_ones, R.color.groupOldOnesPrimary, R.color.groupOldOnesSecondary, R.integer.cultist_cost),
    YELLOW_MASK_ABILITY(R.string.ability_yellow_mask_name, R.string.ability_yellow_mask_description, R.drawable.ic_group_old_ones, R.color.groupOldOnesPrimary, R.color.groupOldOnesSecondary, R.integer.yellow_mask_cost),

    ARCHITECT_ABILITY(R.string.ability_architect_name, R.string.ability_architect_description, R.drawable.ic_group_hellbound, R.color.groupHellboundPrimary, R.color.groupHellboundSecondary, R.integer.architect_cost),
    DOPPELGANGER_ABILITY(R.string.ability_doppelganger_name, R.string.ability_doppelganger_description, R.drawable.ic_group_hellbound, R.color.groupHellboundPrimary, R.color.groupHellboundSecondary, R.integer.doppleganger_cost),
    MARKED_ABILITY(R.string.ability_marked_name, R.string.ability_marked_description, R.drawable.ic_group_hellbound, R.color.groupHellboundPrimary, R.color.groupHellboundSecondary, R.integer.marked_cost),
    HELLSPAWN_ABILITY(R.string.ability_hellspawn_name, R.string.ability_hellspawn_description, R.drawable.ic_group_hellbound, R.color.groupHellboundPrimary, R.color.groupHellboundSecondary, R.integer.hellspawn_cost);

    override val type = PrincipleAbilityType.FACTION
}

enum class PositiveAbility (override val abilityName: Int, override val description: Int, override var cost: Int = 1) : AbilityDefinition {

    BURN(R.string.ability_burn_name, R.string.ability_burn_description, R.integer.burn_cost),
    SACRIFICE(R.string.ability_sacrifice_name, R.string.ability_sacrifice_description, R.integer.sacrifice_cost),
    PARALYZE(R.string.ability_paralyze_name, R.string.ability_paralyze_description, R.integer.paralyze_cost),
    RELENTLESS(R.string.ability_relentless_name, R.string.ability_relentless_description, R.integer.relentless_cost),
    DECAYING(R.string.ability_decaying_name, R.string.ability_decaying_description, R.integer.decaying_cost),
    PROTECTION_GREATER(R.string.ability_protection_greater_name, R.string.ability_protection_greater_description, R.integer.greater_protection_cost),
    PROTECTION_LESSER(R.string.ability_protection_lesser_name, R.string.ability_protection_lesser_description, R.integer.lesser_protection_cost),
    HYPNOSIS(R.string.ability_hypnosis_name, R.string.ability_hypnosis_description, R.integer.hypnosis_cost),
    RITUAL(R.string.ability_ritual_name, R.string.ability_ritual_description, R.integer.ritual_cost),
    LORD(R.string.ability_lord_name, R.string.ability_lord_description, R.integer.lord_cost),
    VINDICTIVE(R.string.ability_vindictive_name, R.string.ability_vindictive_description, R.integer.vindictive_cost),
    CURSE(R.string.ability_curse_name, R.string.ability_curse_description, R.integer.curse_cost),
    WITHER(R.string.ability_wither_name, R.string.ability_wither_description, R.integer.wither_cost),
    POISON(R.string.ability_poison_name, R.string.ability_poison_description, R.integer.poison_cost),
    FATE(R.string.ability_fate_name, R.string.ability_fate_description, R.integer.fate_cost),
    HERESY(R.string.ability_heresy_name, R.string.ability_heresy_description, R.integer.heresy_cost),
    TAXATION(R.string.ability_taxation_name, R.string.ability_taxation_description, R.integer.taxation_cost),
    NECROMANCY(R.string.ability_necromancy_name, R.string.ability_necromancy_description, R.integer.necromancy_cost),
    DARK_REVELATION(R.string.ability_dark_revelation_name, R.string.ability_dark_revelation_description, R.integer.dark_revelation_cost),
    AMNESIA(R.string.ability_amnesia_name, R.string.ability_amnesia_description, R.integer.amnesia_cost),
    FONT(R.string.ability_font_name, R.string.ability_font_description, R.integer.font_cost),
    PRIMAL_NATURE(R.string.ability_primal_nature_name, R.string.ability_primal_nature_description, R.integer.primal_nature_cost),
    ASSASSIN(R.string.ability_assassin_name, R.string.ability_assassin_description, R.integer.assassin_cost),
    SEANCE(R.string.ability_seance_name, R.string.ability_seance_description, R.integer.seance_cost),
    CHAMPION(R.string.ability_champion_name, R.string.ability_champion_description, R.integer.champion_cost),
    UNHOLY_TRANSFORMATION(R.string.ability_unholy_transformation_name, R.string.ability_unholy_transformation_description, R.integer.unholy_transformation_cost),
    HEX(R.string.ability_hex_name, R.string.ability_hex_description, R.integer.hex_cost),
    GRAVE_ROBBER(R.string.ability_grave_robber_name, R.string.ability_grave_robber_description, R.integer.grave_robber_cost),
    DEMONIC_FIDDLER(R.string.ability_demonic_fiddler_name, R.string.ability_demonic_fiddler_description, R.integer.demonic_fiddler_cost),
    FRIGHTENING_ASSAULT(R.string.ability_frightening_assault_name, R.string.ability_frightening_assault_description, R.integer.frightening_assault_cost);

    override val type = PrincipleAbilityType.POSITIVE
    override val icon = R.drawable.ic_beneficial
    override val primaryColor = R.color.abilityTypeBeneficialPrimary
    override val secondaryColor = R.color.abilityTypeBeneficialSecondary
}

enum class NegativeAbility(override val abilityName: Int, override val description: Int, override var cost: Int = 1) : AbilityDefinition {
    FRAGILE(R.string.ability_fragile_name, R.string.ability_fragile_description, R.integer.fragile_cost),
    HUNGRY(R.string.ability_hungry_name, R.string.ability_hungry_description, R.integer.hungry_cost),
    BLOOD_PRICE(R.string.ability_blood_price_name, R.string.ability_blood_price_description, R.integer.blood_price_cost),
    DARK_COST(R.string.ability_dark_cost_name, R.string.ability_dark_cost_description, R.integer.dark_cost_cost),
    VENGEFUL(R.string.ability_vengeful_name, R.string.ability_vengeful_description, R.integer.vengeful_cost),
    COWARDLY(R.string.ability_cowardly_name, R.string.ability_cowardly_description, R.integer.cowardly_cost),
    BRASH(R.string.ability_brash_name, R.string.ability_brash_description, R.integer.brash_cost),
    HOLLOW(R.string.ability_hollow_name, R.string.ability_hollow_description, R.integer.hollow_cost),
    ANCIENT(R.string.ability_ancient_name, R.string.ability_ancient_description, R.integer.ancient_cost),
    UNIQUE(R.string.ability_unique_name, R.string.ability_unique_description, R.integer.unique_cost),
    ETHERIAL(R.string.ability_etherial_name, R.string.ability_etherial_description, R.integer.etherial_cost),
    GHOUL(R.string.ability_ghoul_name, R.string.ability_ghoul_description, R.integer.ghoul_cost),
    SYCOPHANT(R.string.ability_sycophant_name, R.string.ability_sycophant_description, R.integer.sychophant_cost),
    SLOTH(R.string.ability_sloth_name, R.string.ability_sloth_description, R.integer.sloth_cost),
    DEATHS_GIFT(R.string.ability_deaths_gift_name, R.string.ability_deaths_gift_description, R.integer.deaths_gift_cost);

    override val type = PrincipleAbilityType.NEGATIVE
    override val icon = R.drawable.ic_detrimental
    override val primaryColor = R.color.abilityTypeDetrimentalPrimary
    override val secondaryColor = R.color.abilityTypeDetrimentalSecondary
}

enum class ArchetypeAbilities(override val abilityName: Int, override val description: Int, override var cost: Int = 0) : AbilityDefinition {
    ARBITER(R.string.ability_arbiter_name, R.string.ability_arbiter_description, R.integer.arbiter_cost),
    REAPER(R.string.ability_reaper_name, R.string.ability_reaper_description, R.integer.reaper_cost),
    EXECUTIONER(R.string.ability_executioner_name, R.string.ability_executioner_description, R.integer.executioner_cost),
    LUNATIC_PIPER(R.string.ability_lunatic_piper_name, R.string.ability_lunatic_piper_description, R.integer.lunatic_piper_cost),
    LICH(R.string.ability_lich_name, R.string.ability_lich_description, R.integer.lich_cost),
    INQUISITOR(R.string.ability_inquisitor_name, R.string.ability_inquisitor_description, R.integer.inquistor_cost);

    override val type = PrincipleAbilityType.ARCHETYPE
    override val icon = R.drawable.ic_archetype
    override val primaryColor = R.color.abilityTypeArchetypePrimary
    override val secondaryColor = R.color.abilityTypeArchetypeSecondary
}

enum class CombatAbilities(override val abilityName: Int, override val description: Int, override var cost: Int = 1) : AbilityDefinition {
    PHALANX(R.string.ability_phalanx_name, R.string.ability_phalanx_description, R.integer.phalanyx_cost),
    ARTILLERY(R.string.ability_artillery_name, R.string.ability_artillery_description, R.integer.artillery_cost),
    OVERRUN(R.string.ability_overrun_name, R.string.ability_overrun_description, R.integer.overrun_cost),
    FLANKING(R.string.ability_flanking_name, R.string.ability_flanking_description, R.integer.flanking_cost),
    WEDGE(R.string.ability_wedge_name, R.string.ability_wedge_description, R.integer.wedge_cost),
    REACH(R.string.ability_reach_name, R.string.ability_reach_description, R.integer.reach_cost);

    override val type = PrincipleAbilityType.COMBAT
    override val icon = R.drawable.ic_combat
    override val primaryColor = R.color.abilityTypeCombatPrimary
    override val secondaryColor = R.color.abilityTypeCombatSecondary
}