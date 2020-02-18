package org.ajar.umbrallegacy.ui

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.ajar.umbrallegacy.model.Ability
import android.view.LayoutInflater
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import org.ajar.umbrallegacy.R


class AbilityListAdapter(var abilities: LiveData<List<Ability>>) : RecyclerView.Adapter<AbilityListAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return abilities.value?.count()?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ability = abilities.value?.get(position)
        if(ability != null) {
            holder.setValue(ability)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.ability_item, parent, false)

        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var nameTextView: TextView = itemView.findViewById(R.id.ability_name)
        private var typeTextView: TextView = itemView.findViewById(R.id.ability_type)
        private var costTextView: TextView = itemView.findViewById(R.id.ability_cost)

        fun setValue(ability: Ability) {
            nameTextView.text = ability.name
            typeTextView.text = ability.type.displayName(itemView.context)
            costTextView.text = ability.cost.toString()
        }
    }
}