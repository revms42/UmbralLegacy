package org.ajar.umbrallegacy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import org.ajar.umbrallegacy.R
import org.ajar.umbrallegacy.model.AbilityDefinition
import org.ajar.umbrallegacy.model.PrincipleAbilityType


class EditAbilityFragment(private val backFragment: Fragment) : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance(backFragment: Fragment) = EditAbilityFragment(backFragment)
    }

    private lateinit var viewModel: AbilityListViewModel
    private var type: PrincipleAbilityType? = null
    private var spinnerAdapter: ArrayAdapter<CharSequence>? = null
    private var spinner: Spinner? = null
    private var imageView: ImageView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.edit_ability_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(activity!!).get(AbilityListViewModel::class.java)
        type = viewModel.selectedAbility?.type

        val nameTextBox = view?.findViewById<EditText>(R.id.edit_ability_name_text)?.also { it.setText(viewModel.selectedAbility?.name) }
        val descTextBox = view?.findViewById<EditText>(R.id.edit_ability_description)?.also { it.setText(viewModel.selectedAbility?.description) }
        val costTextBox = view?.findViewById<EditText>(R.id.edit_ability_cost)?.also { it.setText(viewModel.selectedAbility?.cost.toString()) }

        spinnerAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.ability_types, android.R.layout.simple_spinner_dropdown_item)
        spinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner = view?.findViewById(R.id.edit_ability_type_spinner)
        spinner?.adapter = spinnerAdapter
        spinner?.onItemSelectedListener = this
        type?.also { setSelection(it) }

        imageView = view?.findViewById(R.id.edit_ability_type_image)

        view?.findViewById<Button>(R.id.accept_button)?.setOnClickListener {
            //TODO: Make a change.
            viewModel.selectedAbility?.also { ability ->
                ability.name = nameTextBox?.text.toString()
                ability.description = descTextBox?.text.toString()
                ability.cost = costTextBox?.text.toString().toInt()
                type?.also { t -> ability.type = t }

                viewModel.commitSelected()
            }

            activity!!.supportFragmentManager.beginTransaction().remove(this).show(backFragment).commit()
        }
        view?.findViewById<Button>(R.id.cancel_button)?.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction().remove(this).show(backFragment).commit()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        val item = parent?.getItemAtPosition(pos)
        val selected = parent?.getItemAtPosition(pos).toString()

        selected.also { name ->
            PrincipleAbilityType.fromDisplayName(requireContext(),name)?.takeIf { it.icon != -1 }?.also {
                type = it
                imageView?.setImageDrawable(requireContext().getDrawable(it.icon))
            }
        }
    }

    private fun setSelection(type: PrincipleAbilityType) {
        val index = spinnerAdapter?.getPosition(type.displayName(requireContext()))?: 0
        spinner?.setSelection(index)
    }

}
