package org.ajar.umbrallegacy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import org.ajar.umbrallegacy.AbilityListActivity
import org.ajar.umbrallegacy.R


class EditAbilityFragment(private val backFragment: Fragment) : Fragment() {

    companion object {
        fun newInstance(backFragment: Fragment) = EditAbilityFragment(backFragment)
    }

    private lateinit var viewModel: AbilityListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.edit_ability_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this.activity!!).get(AbilityListViewModel::class.java)

        this.view?.findViewById<EditText>(R.id.edit_ability_name_text)?.setText(viewModel.selectedAbility?.name)
        this.view?.findViewById<EditText>(R.id.edit_ability_description)?.setText(viewModel.selectedAbility?.description)

        this.view?.findViewById<Spinner>(R.id.edit_ability_type_spinner)?.adapter

        this.view?.findViewById<Button>(R.id.accept_button)?.setOnClickListener {
            //TODO: Make a change.
            this.activity!!.supportFragmentManager.beginTransaction().remove(this).commit()
        }
        this.view?.findViewById<Button>(R.id.accept_button)?.setOnClickListener {
            this.activity!!.supportFragmentManager.beginTransaction().remove(this).show(backFragment).commit()
        }
    }

}
