package org.ajar.umbrallegacy.model

import android.content.Context
import org.ajar.umbrallegacy.R

enum class FactionType {
    PRIMARY,
    SECONDARY,
    SUBSERVIENT
}

interface FactionDefinition {

    val factionName: Int
    val member: Int
    val members: Int
    val type: FactionType
    val ability: FactionAbility
    val opposedFaction: MutableList<FactionDefinition>
    val subFaction: MutableList<FactionDefinition>

    fun hrName(context: Context): String {
        return context.getString(factionName)
    }

    fun hrMember(context: Context): String {
        return context.getString(member)
    }

    fun hrMembers(context: Context): String {
        return context.getString(members)
    }

    fun debugDescription(context: Context): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("${hrName(context)} ($type): \n\tOpposed: \n")
        opposedFaction.forEach { opp -> stringBuilder.append("\t\t${opp.hrName(context)}\n") }
        stringBuilder.append("\tSubservient: \n")
        subFaction.forEach { sub -> stringBuilder.append("\t\t${sub.hrName(context)}\n") }
        stringBuilder.append("\tAbility: \n")
        stringBuilder.append("\t\t${ability.name(context)}\n")
        stringBuilder.append("\t\t${ability.describe(context)}\n")
        return stringBuilder.toString()
    }
}

enum class Faction(override val factionName: Int, override val member: Int, override val members: Int, override val type: FactionType, override val ability: FactionAbility) : FactionDefinition {
    VAMPIRES(R.string.faction_vampires_name, R.string.faction_vampires_member, R.string.faction_vampires_members, FactionType.PRIMARY, FactionAbility.VAMPIRE_ABILITY),
    NOSFERATU(R.string.faction_nosferatu_name, R.string.faction_nosferatu_member, R.string.faction_nosferatu_members, FactionType.PRIMARY, FactionAbility.NOSFERATU_ABILITY),
    WIGHTS(R.string.faction_wights_name, R.string.faction_wights_member, R.string.faction_wights_members, FactionType.SECONDARY, FactionAbility.WIGHT_ABILITY),
    ZOMBIES(R.string.faction_zombies_name, R.string.faction_zombies_member, R.string.faction_zombies_members, FactionType.SUBSERVIENT, FactionAbility.ZOMBIE_ABILITY),

    LYCANTHROPES(R.string.faction_lycanthropes_name, R.string.faction_lycanthropes_member, R.string.faction_lycanthropes_members, FactionType.SUBSERVIENT, FactionAbility.LYCANTHROPE_ABILITY),
    ANIMATED(R.string.faction_animated_name, R.string.faction_animated_member, R.string.faction_animated_members, FactionType.SECONDARY, FactionAbility.ANIMATED_ABILITY),
    TOTEMIC_SPIRITS(R.string.faction_totemic_spirits_name, R.string.faction_totemic_spirits_member, R.string.faction_totemic_spirits_members, FactionType.PRIMARY, FactionAbility.TOTEMIC_SPIRIT_ABILITY),
    WITCH_BOUND(R.string.faction_witch_bound_name, R.string.faction_witch_bound_member, R.string.faction_witch_bound_members, FactionType.PRIMARY, FactionAbility.WITCH_BOUND_ABILITY),

    WARLOCKS(R.string.faction_warlocks_name, R.string.faction_warlocks_member, R.string.faction_warlocks_members, FactionType.PRIMARY, FactionAbility.WARLOCK_ABILITY),
    ELEMENTALS(R.string.faction_elementals_name, R.string.faction_elementals_member, R.string.faction_elementals_members, FactionType.SUBSERVIENT, FactionAbility.ELEMENTAL_ABILITY),
    BOUND_DEMONS(R.string.faction_bound_demons_name, R.string.faction_bound_demons_member, R.string.faction_bound_demons_members, FactionType.SUBSERVIENT, FactionAbility.BOUND_DEMON_ABILITY),
    MAGICAL_CONSTRUCTS(R.string.faction_magical_constructs_name, R.string.faction_magical_constructs_member, R.string.faction_magical_constructs_members, FactionType.SUBSERVIENT, FactionAbility.MAGICAL_CONSTRUCT_ABILITY),

    HORNED_ONES(R.string.faction_horned_ones_name, R.string.faction_horned_ones_member, R.string.faction_horned_ones_members, FactionType.PRIMARY, FactionAbility.HORNED_ONE_ABILITY),
    WISH_GRANTERS(R.string.faction_wish_granters_name, R.string.faction_wish_granters_member, R.string.faction_wish_granters_members, FactionType.SECONDARY, FactionAbility.WISH_GRANTER_ABILITY),
    UNSEELIE(R.string.faction_unseelie_name, R.string.faction_unseelie_member, R.string.faction_unseelie_members, FactionType.PRIMARY, FactionAbility.UNSEELIE_ABILITY),
    TROLLS(R.string.faction_trolls_name, R.string.faction_trolls_member, R.string.faction_trolls_members, FactionType.SUBSERVIENT, FactionAbility.TROLL_ABILITY),

    GREAT_OLD_ONES(R.string.faction_great_old_ones_name, R.string.faction_great_old_ones_member, R.string.faction_great_old_ones_members, FactionType.PRIMARY, FactionAbility.GREAT_OLD_ONE_ABILITY),
    CULTISTS(R.string.faction_cultists_name, R.string.faction_cultists_member, R.string.faction_cultists_members, FactionType.SUBSERVIENT, FactionAbility.CULTIST_ABILITY),
    ANCIENT_RACE(R.string.faction_ancient_race_name, R.string.faction_ancient_race_member, R.string.faction_ancient_race_members, FactionType.PRIMARY, FactionAbility.ANCIENT_RACE_ABILITY),
    YELLOW_MASKS(R.string.faction_yellow_masks_name, R.string.faction_yellow_masks_member, R.string.faction_yellow_masks_members, FactionType.SECONDARY, FactionAbility.YELLOW_MASK_ABILITY),

    ARCHITECTS(R.string.faction_architects_name, R.string.faction_architects_member, R.string.faction_architects_members, FactionType.PRIMARY, FactionAbility.ARCHITECT_ABILITY),
    DOPPLEGANGERS(R.string.faction_doppelgangers_name, R.string.faction_doppelgangers_member, R.string.faction_doppelgangers_members, FactionType.SUBSERVIENT, FactionAbility.DOPPELGANGER_ABILITY),
    MARKED(R.string.faction_marked_name, R.string.faction_marked_member, R.string.faction_marked_members, FactionType.PRIMARY, FactionAbility.MARKED_ABILITY),
    HELLSPAWN(R.string.faction_hellspawn_name, R.string.faction_hellspawn_member, R.string.faction_hellspawn_members, FactionType.SUBSERVIENT, FactionAbility.HELLSPAWN_ABILITY);

    override val opposedFaction: MutableList<FactionDefinition> = ArrayList()
    override val subFaction: MutableList<FactionDefinition> = ArrayList()

    companion object {
        private var _setup: Boolean = false

        fun setup() {
            if (_setup) return
            VAMPIRES.opposedFaction.add(NOSFERATU)
            VAMPIRES.subFaction.add(ZOMBIES)

            NOSFERATU.opposedFaction.add(VAMPIRES)
            NOSFERATU.subFaction.add(ZOMBIES)

            WIGHTS.subFaction.add(ZOMBIES)

            TOTEMIC_SPIRITS.opposedFaction.add(WITCH_BOUND)
            TOTEMIC_SPIRITS.subFaction.add(LYCANTHROPES)

            WITCH_BOUND.opposedFaction.add(TOTEMIC_SPIRITS)
            WITCH_BOUND.subFaction.add(LYCANTHROPES)

            ANIMATED.subFaction.add(LYCANTHROPES)

            WARLOCKS.subFaction.add(ELEMENTALS)
            WARLOCKS.subFaction.add(BOUND_DEMONS)
            WARLOCKS.subFaction.add(MAGICAL_CONSTRUCTS)

            HORNED_ONES.opposedFaction.add(UNSEELIE)
            HORNED_ONES.subFaction.add(TROLLS)

            UNSEELIE.opposedFaction.add(HORNED_ONES)
            UNSEELIE.subFaction.add(TROLLS)

            WISH_GRANTERS.subFaction.add(TROLLS)

            GREAT_OLD_ONES.opposedFaction.add(ANCIENT_RACE)
            GREAT_OLD_ONES.subFaction.add(CULTISTS)

            ANCIENT_RACE.opposedFaction.add(GREAT_OLD_ONES)
            ANCIENT_RACE.subFaction.add(CULTISTS)

            YELLOW_MASKS.subFaction.add(CULTISTS)

            ARCHITECTS.opposedFaction.add(MARKED)
            ARCHITECTS.subFaction.add(HELLSPAWN)

            MARKED.opposedFaction.add(ARCHITECTS)
            MARKED.subFaction.add(DOPPLEGANGERS)

            _setup = true
        }
    }
}


open class GroupDefinition(
        val name: Int,
        val member: Int,
        val members: Int,
        vararg val factions: Faction
)

sealed class Group(name: Int, member: Int, members: Int, vararg factions: Faction) : GroupDefinition(name, member, members, *factions) {
    object Stillblood : Group(R.string.group_stillblood_name, R.string.group_stillblood_member, R.string.group_stillblood_members, Faction.VAMPIRES, Faction.NOSFERATU, Faction.WIGHTS, Faction.ZOMBIES)
    object Cursed : Group(R.string.group_cursed_name, R.string.group_cursed_member, R.string.group_cursed_members, Faction.LYCANTHROPES, Faction.ANIMATED, Faction.TOTEMIC_SPIRITS, Faction.WITCH_BOUND)
    object Piercers : Group(R.string.group_piercers_name, R.string.group_piercers_member, R.string.group_piercers_members, Faction.WARLOCKS, Faction.ELEMENTALS, Faction.BOUND_DEMONS, Faction.MAGICAL_CONSTRUCTS)
    object Fae : Group(R.string.group_fae_name, R.string.group_fae_member, R.string.group_fae_members, Faction.TROLLS, Faction.WISH_GRANTERS, Faction.UNSEELIE, Faction.HORNED_ONES)
    object OldOnes : Group(R.string.group_old_ones_name, R.string.group_old_ones_member, R.string.group_old_ones_members, Faction.GREAT_OLD_ONES, Faction.ANCIENT_RACE, Faction.YELLOW_MASKS, Faction.CULTISTS)
    object Hellbound : Group(R.string.group_hellbound_name, R.string.group_hellbound_member, R.string.group_hellbound_members, Faction.ARCHITECTS, Faction.MARKED, Faction.HELLSPAWN, Faction.DOPPLEGANGERS)
}