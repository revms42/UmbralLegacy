package org.ajar.umbrallegacy.ui.card

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.ajar.umbrallegacy.R
import org.ajar.umbrallegacy.model.Faction

class CardViewActivity : AppCompatActivity() {

    private lateinit var cardViewModel: CardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.card_view_activity)

        val nextFaction = fun(faction: Faction?): Faction {
            return faction?.let {
                val nextOrdinal = if(it.ordinal <= Faction.values().size -2) it.ordinal + 1 else 0
                Faction.values()[nextOrdinal]
            }?: Faction.VAMPIRES
        }

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            cardViewModel.faction = nextFaction(cardViewModel.faction)
            view.invalidate()
        }
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)

        cardViewModel = ViewModelProvider(fragment).get(CardViewModel::class.java)
    }
}