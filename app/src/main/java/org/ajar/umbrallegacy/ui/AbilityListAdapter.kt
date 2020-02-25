package org.ajar.umbrallegacy.ui

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.ajar.umbrallegacy.model.Ability
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import org.ajar.umbrallegacy.R

class AbilityListAdapter(private val abilities: LiveData<List<Ability>>, private val itemSelectionListener: ItemSelectionListener<Ability>) : RecyclerView.Adapter<AbilityListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return abilities.value?.count()?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ability = abilities.value?.get(position)
        if(ability != null) {
            holder.bind(ability, itemSelectionListener)

            if(holder.itemView.isSelected) {
                holder.itemView.isSelected = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        // Inflate the custom layout
        val abilityRowView = inflater.inflate(R.layout.ability_item, parent, false)

        // Return a new holder instance
        return ViewHolder(abilityRowView)
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var nameTextView: TextView = itemView.findViewById(R.id.ability_name)
        private var typeIcon: ImageView = itemView.findViewById(R.id.ability_type)
        private var costTextView: TextView = itemView.findViewById(R.id.ability_cost)

        fun bind(ability: Ability, itemSelectionListener: ItemSelectionListener<Ability>) {
            nameTextView.text = ability.name
            typeIcon.setImageDrawable(itemView.context.getDrawable(ability.type.icon))
            costTextView.text = ability.cost.toString()

            itemView.setOnClickListener {
                itemView.isSelected = !itemView.isSelected
                itemSelectionListener.onSelect(ability, itemView)
            }
        }
    }
}