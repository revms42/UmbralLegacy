package org.ajar.umbrallegacy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.ajar.umbrallegacy.ui.*

class AbilityListActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.ability_activity)

        this.supportFragmentManager.beginTransaction().add(R.id.fragment_frame, AbilityListFragment.newInstance()).commitAllowingStateLoss()
    }
}