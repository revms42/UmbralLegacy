package org.ajar.umbrallegacy.model

import org.ajar.umbrallegacy.R

sealed class Cost(private val prefix: String) {
    class GroupCost(var group: Group) : Cost(GROUP_PREFIX) {
        override fun toString(): String {
            return "${super.toString()}:${group.ordinal}"
        }

        override val icon: Image
            get() = group.icon

        override val costIcon: Image
            get() = group.costIcon

        companion object {
            fun fromString(str: String): GroupCost {
                return GroupCost(Group.values()[str.split(":")[1].toInt()])
            }
        }
    }
    class OpenCost : Cost(OPEN_PREFIX) {
        override val icon: Image = Image(R.drawable.ic_cost)
        override val costIcon: Image = Image(R.drawable.ic_payment_blood)
    }

    override fun toString(): String = prefix

    abstract val icon: Image
    abstract val costIcon: Image

    companion object {
        const val GROUP_PREFIX = "cost-group"
        const val OPEN_PREFIX = "cost-open"

        fun fromString(str: String) : Cost? {
            return if(str.startsWith("cost-group")) {
                GroupCost.fromString(str)
            } else {
                OpenCost()
            }
        }
    }
}