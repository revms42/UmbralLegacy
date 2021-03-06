package org.ajar.umbrallegacy.ui.ability

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.ajar.umbrallegacy.R
import org.ajar.umbrallegacy.model.Ability

class AbilityListFragment : Fragment(),
    ItemSelectionListener<Ability> {

    companion object {
        fun newInstance() =
            AbilityListFragment()
    }

    private lateinit var viewModel: AbilityListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_ability_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AbilityListViewModel::class.java)

        val abilityRV = this.view?.findViewById<RecyclerView>(R.id.rvAbilities)
        val adapter = AbilityListAdapter(
            viewModel.abilityLD,
            this
        )
        abilityRV?.layoutManager = LinearLayoutManager(requireActivity())
        abilityRV?.adapter = adapter

        viewModel.abilityLD.observe(requireActivity(), Observer {
            Log.d("${this::class.java}", "$it")
            adapter.notifyDataSetChanged()
        })
    }

    override fun onSelect(item: Ability, view: View) {
        viewModel.selectedAbility = item
        findNavController().navigate(AbilityListFragmentDirections.actionAbilityListFragmentToEditAbilityFragment())
    }
}