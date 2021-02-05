package org.ajar.umbrallegacy.ui.ability

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.ajar.umbrallegacy.R

class AbilityListActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.ability_activity)

        this.supportFragmentManager.beginTransaction().add(R.id.fragment_frame, AbilityListFragment.newInstance()).commitAllowingStateLoss()
    }
}