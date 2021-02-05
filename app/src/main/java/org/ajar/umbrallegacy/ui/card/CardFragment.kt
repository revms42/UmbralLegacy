package org.ajar.umbrallegacy.ui.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import org.ajar.umbrallegacy.content.UmbralDatabase
import org.ajar.umbrallegacy.model.Card
import org.ajar.umbrallegacy.model.CardLayout
import org.ajar.umbrallegacy.model.Faction
import org.ajar.umbrallegacy.model.Group
import org.ajar.umbrallegacy.ui.NavActivityViewModel
import org.ajar.umbrallegacy.ui.NavigationActivity

class CardFragment : Fragment() {

    private lateinit var viewModel: CardViewModel
    private var navModel: NavActivityViewModel? = null
    private lateinit var card: Card
    private val args: CardFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        card = if(args.card != -1) {
            UmbralDatabase.cards?.findById(args.card)?: Card()
        } else {
            Card()
        }

        return inflater.inflate(card.layout.layoutRes?: CardLayout.IMAGE_TEXT_HORIZONTAL_SPLIT.layoutRes!!, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CardViewModel::class.java)

        if(requireActivity() is NavigationActivity) {
            navModel = ViewModelProvider(requireActivity()).get(NavActivityViewModel::class.java)

            val nextFaction = fun(faction: Faction?): Faction {
                return faction?.let {
                    val nextOrdinal = if(it.ordinal <= Faction.values().size -2) it.ordinal + 1 else 0
                    Faction.values()[nextOrdinal]
                }?: Faction.VAMPIRES
            }

            navModel?.fabAction = fun() {
                viewModel.faction = nextFaction(viewModel.faction)
                navModel?.fabIcon = Group.findGroup(viewModel.faction).icon
            }
        } else {
            navModel = null
        }
    }
    
    override fun onResume() {
        super.onResume()
        view?.also { viewModel.wireUpView(it, requireActivity()) }
        viewModel.card = card
    }

    override fun onPause() {
        super.onPause()
        viewModel.unWireView(this)
        navModel?.fabAction = null
    }

    //TODO: Two things left to do. Redo everything if the card gets changed.
}