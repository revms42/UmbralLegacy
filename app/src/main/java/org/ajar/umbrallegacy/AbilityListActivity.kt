package org.ajar.umbrallegacy

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.ajar.umbrallegacy.ui.AbilityListAdapter
import org.ajar.umbrallegacy.ui.AbilityListViewModel

class AbilityListActivity : AppCompatActivity() {

    private val viewModel: AbilityListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.ability_list)

        val abilityRV = findViewById<RecyclerView>(R.id.rvAbilities)
        val adapter = AbilityListAdapter(viewModel.abilityLD)
        abilityRV.adapter = adapter

        abilityRV.layoutManager = LinearLayoutManager(this)

        viewModel.abilityLD.observe(this, Observer {
            Log.d("${this::class.java}", "$it")
            adapter.notifyDataSetChanged()
        })
    }
}