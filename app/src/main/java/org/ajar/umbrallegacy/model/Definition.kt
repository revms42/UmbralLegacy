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

sealed class Faction(name: Int, member: Int, members: Int, type: FactionType) : FactionDefinition(name, member, members, type) {
    object Vampire : Faction("Vampire", FactionType.PRIMARY)
    object Nosferatu : Faction("Nosferatu", FactionType.PRIMARY)
    object Wight : Faction("Wight", FactionType.SECONDARY)
    object Zombie : Faction("Zombie", FactionType.SUBSERVIENT)

    object Lycanthrope : Faction("Lycanthrope", FactionType.SUBSERVIENT)
    object Animated : Faction("Animated", FactionType.SECONDARY,"Animated")
    object TotemicSpirit : Faction("Totemic Spirit", FactionType.PRIMARY)
    object WitchBound : Faction("Witch Bound", FactionType.PRIMARY,"Witch Bound")

    object Warlock : Faction("Warlock/Witch", FactionType.PRIMARY,"Warlocks/Witches")
    object Elemental : Faction("Elemental", FactionType.SUBSERVIENT)
    object BoundDemon : Faction("Bound Demon", FactionType.SUBSERVIENT)
    object MagicalConstruct : Faction("Magical Construct", FactionType.SUBSERVIENT)

    object HornedOne : Faction("Horned One", FactionType.PRIMARY)
    object WishGranter : Faction("Wish Granter", FactionType.SECONDARY)
    object Unseelie: Faction("Unseelie", FactionType.PRIMARY, "Unseelie")
    object Troll: Faction("Troll", FactionType.SUBSERVIENT)

    object GreatOldOne: Faction("Great Old One", FactionType.PRIMARY)
    object Cultist: Faction("Cultist", FactionType.SUBSERVIENT)
    object AncientRace : Faction("Ancient", FactionType.PRIMARY, "Ancient", "The Ancient Race")
    object YellowMask : Faction("Yellow Mask", FactionType.SECONDARY)

    object Architect: Faction("Architect", FactionType.PRIMARY)
    object Doppleganger: Faction("Doppelganger", FactionType.SUBSERVIENT)
    object Marked: Faction("Marked", FactionType.PRIMARY, "Marked")
    object Hellspawn: Faction("Hellspawn", FactionType.SUBSERVIENT, "Hellspawn")

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