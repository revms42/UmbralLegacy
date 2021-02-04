package org.ajar.umbrallegacy.ui.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.ajar.umbrallegacy.R
import org.ajar.umbrallegacy.content.UmbralDatabase
import org.ajar.umbrallegacy.model.Card
import org.ajar.umbrallegacy.model.CardLayout
import org.ajar.umbrallegacy.model.Image

class CardFragment(private var card: Card) : Fragment() {
    private lateinit var viewModel: CardViewModel
    
    companion object {
        fun newInstance(card: Int? = null): CardFragment {
            return CardFragment(if(card == null) {
                Card()
            } else {
                UmbralDatabase.cards?.findById(card)?: Card()
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(card.layout.layoutRes?: CardLayout.IMAGE_TEXT_HORIZONTAL_SPLIT.layoutRes!!, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CardViewModel::class.java)
    }
    
    override fun onResume() {
        super.onResume()
        
        view?.also { view ->
            val cardTextView = view.findViewById<TextView>(R.id.cardText)
            viewModel.cardTextLD.observe(requireActivity(), Observer { 
                cardTextView.text = it
            })
            
            val cardImageView = view.findViewById<ImageView>(R.id.cardImage)
            viewModel.cardImageLD.observe(requireActivity(), Observer { image -> 
                if(image != null) {
                    cardImageView.setImageDrawable(image.getDrawable(resources))
                    cardImageView.visibility = View.VISIBLE
                } else {
                    cardImageView.visibility = View.INVISIBLE
                }
            })
            
            val cardFlavorView = view.findViewById<TextView>(R.id.flavorText)
            viewModel.flavorTextLD.observe(requireActivity(), Observer { 
                cardFlavorView.text = it
            })
            
            val factionImageView = view.findViewById<ImageView>(R.id.factionImage)
            viewModel.factionImageLD.observe(requireActivity(), Observer { image -> 
                if(image != null) {
                    factionImageView.setImageDrawable(image.getDrawable(resources))
                    factionImageView.visibility = View.VISIBLE
                } else {
                    factionImageView.visibility = View.INVISIBLE
                }
            })
            
            val archetypeImageView = view.findViewById<ImageView>(R.id.archetypeImage)
            viewModel.archetypeImageLD.observe(requireActivity(), Observer { image ->
                if(image != null) {
                    archetypeImageView.setImageDrawable(image.getDrawable(resources))
                    archetypeImageView.visibility = View.VISIBLE
                } else {
                    archetypeImageView.visibility = View.INVISIBLE
                }
            })
            
            val abilityImageOneView = view.findViewById<ImageView>(R.id.abilityOneImage)
            val abilityImageTwoView = view.findViewById<ImageView>(R.id.abilityTwoImage)
            val abilityImageThreeView = view.findViewById<ImageView>(R.id.abilityThreeImage)
            
            val abilityImageFunc = fun(list: List<Image?>) {
                abilityImageOneView.visibility = View.INVISIBLE
                abilityImageTwoView.visibility = View.INVISIBLE
                abilityImageThreeView.visibility = View.INVISIBLE
                when {
                    list.isNotEmpty() && list[0] != null -> {
                        abilityImageOneView.visibility = View.VISIBLE
                        abilityImageOneView.setImageDrawable(list[0]!!.getDrawable(resources))
                    }
                    list.size > 1 && list[1] != null -> {
                        abilityImageTwoView.visibility = View.VISIBLE
                        abilityImageTwoView.setImageDrawable(list[1]!!.getDrawable(resources))
                    }
                    list.size > 2 && list[2] != null -> {
                        abilityImageThreeView.visibility = View.VISIBLE
                        abilityImageThreeView.setImageDrawable(list[2]!!.getDrawable(resources))
                    }
                }
            }
            
            viewModel.abilityImagesLD.observe(requireActivity(), Observer { list ->
                abilityImageFunc(list)
            })
            
            viewModel.showAbilityIconsLD.observe(requireActivity(), Observer { 
                if(it) {
                    abilityImageFunc(viewModel.abilityImagesLD.value?: emptyList())
                } else {
                    abilityImageFunc(emptyList())
                }
            })
            
            val costOneImageR = view.findViewById<ImageView>(R.id.costOneImageR)
            val costTwoImageR = view.findViewById<ImageView>(R.id.costTwoImageR)
            val costThreeImageR = view.findViewById<ImageView>(R.id.costThreeImageR)
            val costFourImageR = view.findViewById<ImageView>(R.id.costFourImageR)
            val costFiveImageR = view.findViewById<ImageView>(R.id.costFiveImageR)
            val costSixImageR = view.findViewById<ImageView>(R.id.costSixImageR)
            
            val costTypeOneR = view.findViewById<ImageView>(R.id.costTypeOneR)
            val costTypeTwoR = view.findViewById<ImageView>(R.id.costTypeTwoR)
            val costTypeThreeR = view.findViewById<ImageView>(R.id.costTypeThreeR)
            val costTypeFourR = view.findViewById<ImageView>(R.id.costTypeFourR)
            val costTypeFiveR = view.findViewById<ImageView>(R.id.costTypeFiveR)
            
            val attackImageR = view.findViewById<ImageView>(R.id.attackR)
            val defenseImageR = view.findViewById<ImageView>(R.id.defenseR)
            val nameR = view.findViewById<TextView>(R.id.nameR)

            val imagesR = arrayOf(
                costOneImageR, costTwoImageR, costThreeImageR, 
                costFourImageR, costFiveImageR, costSixImageR,
                costTypeOneR, costTypeTwoR, costTypeThreeR,
                costTypeFourR, costTypeFiveR, attackImageR, defenseImageR, nameR
            )

            val costOneImageL = view.findViewById<ImageView>(R.id.costOneImageL)
            val costTwoImageL = view.findViewById<ImageView>(R.id.costTwoImageL)
            val costThreeImageL = view.findViewById<ImageView>(R.id.costThreeImageL)
            val costFourImageL = view.findViewById<ImageView>(R.id.costFourImageL)
            val costFiveImageL = view.findViewById<ImageView>(R.id.costFiveImageL)
            val costSixImageL = view.findViewById<ImageView>(R.id.costSixImageL)

            val costTypeOneL = view.findViewById<ImageView>(R.id.costTypeOneL)
            val costTypeTwoL = view.findViewById<ImageView>(R.id.costTypeTwoL)
            val costTypeThreeL = view.findViewById<ImageView>(R.id.costTypeThreeL)
            val costTypeFourL = view.findViewById<ImageView>(R.id.costTypeFourL)
            val costTypeFiveL = view.findViewById<ImageView>(R.id.costTypeFiveL)

            val attackImageL = view.findViewById<ImageView>(R.id.attackL)
            val defenseImageL = view.findViewById<ImageView>(R.id.defenseL)
            val nameL = view.findViewById<TextView>(R.id.nameL)

            val imagesL = arrayOf(
                costOneImageL, costTwoImageL, costThreeImageL,
                costFourImageL, costFiveImageL, costSixImageL,
                costTypeOneL, costTypeTwoL, costTypeThreeL,
                costTypeFourL, costTypeFiveL, attackImageL, defenseImageL, nameL
            )

            val hide = fun(images: Array<View>) {
                images.forEach { it.visibility = View.INVISIBLE }
            }
            
            val rightOrientationFunc = fun() {
                viewModel.cost.forEachIndexed { index, cost ->
                    val costImage = when(index) {
                        0 -> costOneImageR
                        1 -> costTwoImageR
                        2 -> costThreeImageR
                        3 -> costFourImageR
                        4 -> costFiveImageR
                        5 -> costSixImageR
                        else -> null
                    }
                    
                    costImage?.also { 
                        if(cost == null) {
                            it.visibility = View.INVISIBLE
                        } else {
                            it.visibility = View.VISIBLE
                            it.setImageDrawable(cost.icon.getDrawable(resources))
                        }
                    }
                }
                viewModel.costType.forEachIndexed { index, cost -> 
                    val costTypeImage = when(index){
                        0 -> costTypeOneR
                        1 -> costTypeTwoR
                        2 -> costTypeThreeR
                        3 -> costTypeFourR
                        4 -> costTypeFiveR
                        else -> null
                    }
                    
                    costTypeImage?.also { 
                        if(cost == null) {
                            it.visibility = View.INVISIBLE
                        } else {
                            it.visibility = View.VISIBLE
                            it.setImageDrawable(cost.costIcon.getDrawable(resources))
                        }
                    }
                }
                attackImageR.setImageDrawable(AttributeImage.forInt(viewModel.attack).attackImage.getDrawable(resources))
                defenseImageR.setImageDrawable(AttributeImage.forInt(viewModel.defense).attackImage.getDrawable(resources))
                nameR.text = viewModel.cardName
            }
            
            val leftOrientationFunc = fun() {
                viewModel.cost.forEachIndexed { index, cost ->
                    val costImage = when(index) {
                        0 -> costOneImageL
                        1 -> costTwoImageL
                        2 -> costThreeImageL
                        3 -> costFourImageL
                        4 -> costFiveImageL
                        5 -> costSixImageL
                        else -> null
                    }

                    costImage?.also {
                        if(cost == null) {
                            it.visibility = View.INVISIBLE
                        } else {
                            it.visibility = View.VISIBLE
                            it.setImageDrawable(cost.icon.getDrawable(resources))
                        }
                    }
                }
                viewModel.costType.forEachIndexed { index, cost ->
                    val costTypeImage = when(index){
                        0 -> costTypeOneL
                        1 -> costTypeTwoL
                        2 -> costTypeThreeL
                        3 -> costTypeFourL
                        4 -> costTypeFiveL
                        else -> null
                    }

                    costTypeImage?.also {
                        if(cost == null) {
                            it.visibility = View.INVISIBLE
                        } else {
                            it.visibility = View.VISIBLE
                            it.setImageDrawable(cost.costIcon.getDrawable(resources))
                        }
                    }
                }
                attackImageL.setImageDrawable(AttributeImage.forInt(viewModel.attack).attackImage.getDrawable(resources))
                defenseImageL.setImageDrawable(AttributeImage.forInt(viewModel.defense).attackImage.getDrawable(resources))
                nameL.text = viewModel.cardName
            }
            
            viewModel.showRightCostBarLD.observe(requireActivity(), Observer { 
                if(it) {
                    hide(imagesL)
                    rightOrientationFunc()
                } else {
                    hide(imagesR)
                    leftOrientationFunc()
                }
            })

            viewModel.costLD.observe(requireActivity(), Observer {
                if(viewModel.showRightCostBar) {
                    hide(imagesL)
                    rightOrientationFunc()
                } else {
                    hide(imagesR)
                    leftOrientationFunc()
                }
            })
            viewModel.costTypeLD.observe(requireActivity(), Observer {
                if(viewModel.showRightCostBar) {
                    hide(imagesL)
                    rightOrientationFunc()
                } else {
                    hide(imagesR)
                    leftOrientationFunc()
                }
            })
        }
    }

    //TODO: Two things left to do. First, unregister observers onPause. Second, redo everything if the card gets changed.
}