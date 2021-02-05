package org.ajar.umbrallegacy.ui.ability

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.ajar.umbrallegacy.R
import org.ajar.umbrallegacy.model.Faction
import org.ajar.umbrallegacy.model.PrincipleAbilityType


class EditAbilityFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var viewModel: AbilityListViewModel

    private var type: PrincipleAbilityType? = null
    private var typeSpinnerAdapter: ArrayAdapter<CharSequence>? = null
    private var typeSpinner: Spinner? = null
    private var typeImageView: ImageView? = null

    private var faction: Faction? = null
    private var factionSpinnerAdapter: ArrayAdapter<CharSequence>? = null
    private var factionSpinner: Spinner? = null
    private var factionImageView: ImageView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_edit_ability, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AbilityListViewModel::class.java)
        type = viewModel.selectedAbility?.type

        val nameTextBox = view?.findViewById<EditText>(R.id.edit_ability_name_text)?.also { it.setText(viewModel.selectedAbility?.name) }
        val descTextBox = view?.findViewById<EditText>(R.id.edit_ability_description)?.also { it.setText(viewModel.selectedAbility?.description) }
        val costTextBox = view?.findViewById<EditText>(R.id.edit_ability_cost)?.also { it.setText(viewModel.selectedAbility?.cost.toString()) }

        setupAbilityTypeSpinner()
        setupFactionTypeSpinner()

        view?.findViewById<Button>(R.id.accept_button)?.setOnClickListener {
            //TODO: Make a change.
            viewModel.selectedAbility?.also { ability ->
                ability.name = nameTextBox?.text.toString()
                ability.description = descTextBox?.text.toString()
                ability.cost = costTextBox?.text.toString().toInt()
                type?.also { t -> ability.type = t }

                viewModel.commitSelected()
            }

            findNavController().navigate(EditAbilityFragmentDirections.actionNavEditAbilityFragmentToNavAbilityListFragment())
        }
        view?.findViewById<Button>(R.id.cancel_button)?.setOnClickListener {
            findNavController().navigate(EditAbilityFragmentDirections.actionNavEditAbilityFragmentToNavAbilityListFragment())
        }
    }

    private fun setupAbilityTypeSpinner() {
        typeSpinnerAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.ability_types, android.R.layout.simple_spinner_dropdown_item)
        typeSpinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        typeSpinner = view?.findViewById(R.id.edit_ability_type_spinner)
        typeSpinner?.adapter = typeSpinnerAdapter
        typeSpinner?.onItemSelectedListener = this
        type?.also { setAbilityTypeSelection(it) }

        typeImageView = view?.findViewById(R.id.edit_ability_type_image)
    }

    private fun setupFactionTypeSpinner() {
        factionSpinnerAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.faction_names, android.R.layout.simple_spinner_dropdown_item)
        factionSpinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        factionSpinner = view?.findViewById(R.id.edit_ability_faction_spinner)
        factionSpinner?.adapter = factionSpinnerAdapter
        factionSpinner?.onItemSelectedListener = this

        factionImageView = view?.findViewById(R.id.edit_ability_type_image)

        factionSpinner?.visibility = View.INVISIBLE
        factionImageView?.visibility = View.INVISIBLE
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        val selected = parent?.getItemAtPosition(pos).toString()

        selected.also { name ->
            PrincipleAbilityType.fromDisplayName(requireContext(),name)?.takeIf { !it.icon.invalid }?.also {
                type = it
                typeImageView?.setImageDrawable(it.icon.getDrawable(resources))

                if(it == PrincipleAbilityType.FACTION) {
                    factionSpinner?.visibility = View.VISIBLE
                    factionImageView?.visibility = View.VISIBLE

                    //TODO: This is dumb but it will get the right image showing.
                    Faction.values().firstOrNull { faction ->  getString(faction.factionName) == viewModel.selectedAbility!!.name }?.also { faction ->
                        this.faction = faction
                        factionImageView?.setImageDrawable(faction.groupIcon.getDrawable(resources))
                    }
                } else {
                    factionSpinner?.visibility = View.INVISIBLE
                    factionImageView?.visibility = View.INVISIBLE
                }
                view?.invalidate()
            }
        }
    }

    private fun setAbilityTypeSelection(type: PrincipleAbilityType) {
        val index = typeSpinnerAdapter?.getPosition(type.displayName(requireContext()))?: 0
        typeSpinner?.setSelection(index)

        if(type == PrincipleAbilityType.FACTION) {
            //TODO: This is dumb but it will get the right image showing.
            Faction.values().firstOrNull { getString(it.factionName) == viewModel.selectedAbility!!.name }?.also {
                setFactionSelection(it)
            }
        }
    }

    private fun setFactionSelection(type: Faction) {
        val index = factionSpinnerAdapter?.getPosition(type.hrName(requireContext()))?: 0
        factionSpinner?.setSelection(index)
    }

}
