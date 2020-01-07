package org.ajar.umbrallegacy.model

import org.ajar.umbrallegacy.R

enum class FactionType {
    PRIMARY,
    SECONDARY,
    SUBSERVIENT
}

open class FactionDefinition (
        val name: Int,
        val member: Int,
        val members: Int,
        val type: FactionType,
        val opposedFaction: MutableList<Faction> = ArrayList(),
        val subFaction: MutableList<Faction> = ArrayList()
) {
    init {
        Faction.setup()
    }
}

sealed class Faction(name: Int, member: Int, members: Int, type: FactionType, val ability: FactionAbility) : FactionDefinition(name, member, members, type) {
    object Vampire : Faction(R.string.faction_vampires_name, R.string.faction_vampires_member, R.string.faction_vampires_members, FactionType.PRIMARY, FactionAbility.VampireFactionAbility)
    object Nosferatu : Faction(R.string.faction_nosferatu_name, R.string.faction_nosferatu_member, R.string.faction_nosferatu_members, FactionType.PRIMARY, FactionAbility.NosferatuFactionAbility)
    object Wight : Faction(R.string.faction_wights_name, R.string.faction_wights_member, R.string.faction_wights_members, FactionType.SECONDARY, FactionAbility.WightFactionAbility)
    object Zombie : Faction(R.string.faction_zombies_name, R.string.faction_zombies_member, R.string.faction_zombies_members, FactionType.SUBSERVIENT, FactionAbility.ZombieFactionAbility)

    object Lycanthrope : Faction(R.string.faction_lycanthropes_name, R.string.faction_lycanthropes_member, R.string.faction_lycanthropes_members, FactionType.SUBSERVIENT, FactionAbility.LycanthropeFactionAbility)
    object Animated : Faction(R.string.faction_animated_name, R.string.faction_animated_member, R.string.faction_animated_members, FactionType.SECONDARY, FactionAbility.AnimatedFactionAbility)
    object TotemicSpirit : Faction(R.string.faction_totemic_spirits_name, R.string.faction_totemic_spirits_member, R.string.faction_totemic_spirits_members, FactionType.PRIMARY, FactionAbility.TotemicSpiritFactionAbility)
    object WitchBound : Faction(R.string.faction_witch_bound_name, R.string.faction_witch_bound_member, R.string.faction_witch_bound_members, FactionType.PRIMARY, FactionAbility.WitchBoundFactionAbility)

    object Warlock : Faction(R.string.faction_warlocks_name, R.string.faction_warlocks_member, R.string.faction_warlocks_members, FactionType.PRIMARY, FactionAbility.WarlockFactionAbility)
    object Elemental : Faction(R.string.faction_elementals_name, R.string.faction_elementals_member, R.string.faction_elementals_members, FactionType.SUBSERVIENT, FactionAbility.ElementalFactionAbility)
    object BoundDemon : Faction(R.string.faction_bound_demons_name, R.string.faction_bound_demons_member, R.string.faction_bound_demons_members, FactionType.SUBSERVIENT, FactionAbility.BoundDemonFactionAbility)
    object MagicalConstruct : Faction(R.string.faction_magical_constructs_name, R.string.faction_magical_constructs_member, R.string.faction_magical_constructs_members, FactionType.SUBSERVIENT, FactionAbility.MagicalConstructFactionAbility)

    object HornedOne : Faction(R.string.faction_horned_ones_name, R.string.faction_horned_ones_member, R.string.faction_horned_ones_members, FactionType.PRIMARY, FactionAbility.HornedOneFactionAbility)
    object WishGranter : Faction(R.string.faction_wish_granters_name, R.string.faction_wish_granters_member, R.string.faction_wish_granters_members, FactionType.SECONDARY, FactionAbility.WishGranterFactionAbility)
    object Unseelie: Faction(R.string.faction_unseelie_name, R.string.faction_unseelie_member, R.string.faction_unseelie_members, FactionType.PRIMARY, FactionAbility.UnseelieFactionAbility)
    object Troll: Faction(R.string.faction_trolls_name, R.string.faction_trolls_member, R.string.faction_trolls_members, FactionType.SUBSERVIENT, FactionAbility.TrollFactionAbility)

    object GreatOldOne: Faction(R.string.faction_great_old_ones_name, R.string.faction_great_old_ones_member, R.string.faction_great_old_ones_members, FactionType.PRIMARY, FactionAbility.GreatOldOneFactionAbility)
    object Cultist: Faction(R.string.faction_cultists_name, R.string.faction_cultists_member, R.string.faction_cultists_members, FactionType.SUBSERVIENT, FactionAbility.CultistFactionAbility)
    object AncientRace : Faction(R.string.faction_ancient_race_name, R.string.faction_ancient_race_member, R.string.faction_ancient_race_members, FactionType.PRIMARY, FactionAbility.AncientRaceFactionAbility)
    object YellowMask : Faction(R.string.faction_yellow_masks_name, R.string.faction_yellow_masks_member, R.string.faction_yellow_masks_members, FactionType.SECONDARY, FactionAbility.YellowMaskFactionAbility)

    object Architect: Faction(R.string.faction_architects_name, R.string.faction_architects_member, R.string.faction_architects_members, FactionType.PRIMARY, FactionAbility.ArchitectFactionAbility)
    object Doppleganger: Faction(R.string.faction_doppelgangers_name, R.string.faction_doppelgangers_member, R.string.faction_doppelgangers_members, FactionType.SUBSERVIENT, FactionAbility.DoppelgangerFactionAbility)
    object Marked: Faction(R.string.faction_marked_name, R.string.faction_marked_member, R.string.faction_marked_members, FactionType.PRIMARY, FactionAbility.MarkedFactionAbility)
    object Hellspawn: Faction(R.string.faction_hellspawn_name, R.string.faction_hellspawn_member, R.string.faction_hellspawn_members, FactionType.SUBSERVIENT, FactionAbility.HellspawnFactionAbility)

    companion object {
        private var _setup: Boolean = false

        fun setup() {
            if(_setup) return
            Vampire.opposedFaction.add(Nosferatu)
            Vampire.subFaction.add(Zombie)

            Nosferatu.opposedFaction.add(Vampire)
            Nosferatu.subFaction.add(Zombie)

            Wight.subFaction.add(Zombie)

            TotemicSpirit.opposedFaction.add(WitchBound)
            TotemicSpirit.subFaction.add(Lycanthrope)

            WitchBound.opposedFaction.add(TotemicSpirit)
            WitchBound.subFaction.add(Lycanthrope)

            Animated.subFaction.add(Lycanthrope)

            Warlock.subFaction.add(Elemental)
            Warlock.subFaction.add(BoundDemon)
            Warlock.subFaction.add(MagicalConstruct)

            HornedOne.opposedFaction.add(Unseelie)
            HornedOne.subFaction.add(Troll)

            Unseelie.opposedFaction.add(HornedOne)
            Unseelie.subFaction.add(Troll)

            WishGranter.subFaction.add(Troll)

            GreatOldOne.opposedFaction.add(AncientRace)
            GreatOldOne.subFaction.add(Cultist)

            AncientRace.opposedFaction.add(GreatOldOne)
            AncientRace.subFaction.add(Cultist)

            YellowMask.subFaction.add(Cultist)

            Architect.opposedFaction.add(Marked)
            Architect.subFaction.add(Hellspawn)

            Marked.opposedFaction.add(Architect)
            Marked.subFaction.add(Doppleganger)

            _setup = true
        }
    }
}


open class GroupDefinition (
    val name: String,
    vararg val factions: Faction
)

sealed class Group(name: String, vararg factions: Faction) {
    object Stillblood : Group("Stillblood", Faction.Vampire, Faction.Nosferatu, Faction.Wight, Faction.Zombie)
    object Cursed : Group("Cursed", Faction.Lycanthrope, Faction.Animated, Faction.TotemicSpirit, Faction.WitchBound)
    object Piercers : Group("Piercers of the Veil", Faction.Warlock, Faction.Elemental, Faction.BoundDemon, Faction.MagicalConstruct)
    object Fae : Group("Fae", Faction.Troll, Faction.WishGranter, Faction.Unseelie, Faction.HornedOne)
    object OldOnes : Group(" Old Ones", Faction.GreatOldOne, Faction.AncientRace, Faction.YellowMask, Faction.Cultist)
    object Hellbound: Group("Hellbound", Faction.Architect, Faction.Marked, Faction.Hellspawn, Faction.Doppleganger)
}