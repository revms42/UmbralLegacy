package org.ajar.umbrallegacy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.ajar.umbrallegacy.R
import org.ajar.umbrallegacy.content.UmbralDatabase

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onResume() {
        super.onResume()

        view?.also {
            it.findViewById<TextView>(R.id.cardsTextView).also { textView ->
                textView.text = getString(R.string.cards_text, UmbralDatabase.cards?.getAll()?.size?: 0)
            }
            it.findViewById<Button>(R.id.cardsButton).setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCardFragment()) //TODO: Card browser instead.
            }

            it.findViewById<TextView>(R.id.abilitiesTextView).text = getString(R.string.abilities_text, UmbralDatabase.abilities?.getAll()?.size?: 0)
            it.findViewById<Button>(R.id.abilitiesButton).setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAbilityListFragment())
            }

            it.findViewById<TextView>(R.id.decksTextView).text = getString(R.string.decks_text, 0)
            it.findViewById<Button>(R.id.decksButton).setOnClickListener {
                //findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAbilityListFragment()) //TODO: Deck browser
            }

            it.findViewById<TextView>(R.id.gameTextView).text = getString(R.string.game_text, "isn't")
            it.findViewById<Button>(R.id.gameButton).setOnClickListener {
                //findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAbilityListFragment()) //TODO: Game play options
            }

            it.invalidate()
        }
    }
}